package org.xzx.plugins;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.xzx.annotation.RobotListener;
import org.xzx.annotation.RobotListenerHandler;
import org.xzx.bean.enums.*;
import org.xzx.bean.messageBean.ReceivedGroupMessage;
import org.xzx.bean.messageUtil.MessageBreaker;
import org.xzx.bean.messageUtil.MessageCounter;
import org.xzx.bean.response.*;
import org.xzx.service.Gocq_service;
import org.xzx.service.GroupImageService;
import org.xzx.service.Jx3_service;
import org.xzx.utils.AliyunOSSUtils;
import org.xzx.utils.CQ_Generator_Utils;
import org.xzx.utils.CQ_String_Utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


//TODO 设置功能在群聊里的权限
@RobotListener
@Log4j2
public class GroupMessageListener {


    @Autowired
    private Gocq_service gocqService;

    @Autowired
    private GroupImageService groupImageService;

    @Autowired
    private Jx3_service jx3Service;

    @Value("${qq.number}")
    private long qq;

    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;

    @Autowired
    private Map<Integer, MessageCounter> messageCounterMap;

    @Autowired
    private AtomicReference<MessageBreaker> messageBreaker;

    @RobotListenerHandler(order = -1)
    public void countMessage(ReceivedGroupMessage receivedGroupMessage) {
        int group_id = receivedGroupMessage.getGroup_id();
        MessageCounter messageCounter = messageCounterMap.get(group_id);
        messageCounter.addMessageCount();
        if (messageCounter.getMessageCount() == messageCounter.getMaxMessageCount()) {
            getRandomImage(group_id);
            messageCounter.setMessageCount(0);
        }
    }

    @RobotListenerHandler(order = 1, shutdown = true)
    public void checkImage(ReceivedGroupMessage receivedGroupMessage) {
        if (receivedGroupMessage.getRaw_message().startsWith("[CQ:image,")) {
            List<String> cqStrings = CQ_String_Utils.getCQStrings(receivedGroupMessage.getRaw_message());
            String imagecq = cqStrings.get(0);
            long poster = receivedGroupMessage.getUser_id();
            long group_id = receivedGroupMessage.getGroup_id();
            if (CQ_String_Utils.ifStaredImage(imagecq)) {
                String imageUrl = CQ_String_Utils.getImageURL(imagecq);
                ApiResponse<CheckImageResponse> checkImageResponseApiResponse = groupImageService.checkUrl(imageUrl, poster, group_id);
                if (checkImageResponseApiResponse.getCode() == ApiResultCode.SUCCESS.getCode() && checkImageResponseApiResponse.getData().getCode() == CheckImageResponseCode.IMAGE_DOWNLOAD_SUCCESS.getCode()) {
                    gocqService.send_group_message(receivedGroupMessage.getGroup_id(), "没见过，偷了");
                } else if (checkImageResponseApiResponse.getCode() == ApiResultCode.SUCCESS.getCode() && checkImageResponseApiResponse.getData().getCode() == CheckImageResponseCode.IMAGE_DOWNLOAD_FAILED.getCode()) {
                    gocqService.send_group_message(receivedGroupMessage.getGroup_id(), "没见过，但没偷成");
                }
            }
            messageBreaker.get().setMessageBreakCode(MessageBreakCode.BREAK);
        }
    }

    /**
     * @param receivedGroupMessage [CQ:reply,id=-635735050][CQ:at,qq=2351200988] [CQ:at,qq=2351200988] 123
     */
    @RobotListenerHandler(order = 1, shutdown = true)
    public void imageReplyActions(ReceivedGroupMessage receivedGroupMessage) {
        int group_id = receivedGroupMessage.getGroup_id();
        long poster = receivedGroupMessage.getUser_id();
        String raw_message = receivedGroupMessage.getRaw_message();
        if (!raw_message.startsWith("[CQ:reply,") || !raw_message.endsWith("图片")) {
            return;
        }
        try {
            String raw_picture_url = getRawPictureUrl(raw_message);
            handleImageActions(raw_message, raw_picture_url, group_id, poster);
        } catch (Exception e) {
            log.error("在处理回复消息中出现问题", e);
        } finally {
            messageBreaker.get().setMessageBreakCode(MessageBreakCode.BREAK);
        }

    }


    @RobotListenerHandler(order = 1, concurrency = true, shutdown = true)
    public void getRandomImage(ReceivedGroupMessage receivedGroupMessage) {
        log.info(receivedGroupMessage.getRaw_message());
        if (receivedGroupMessage.getRaw_message().equals(CQ_Generator_Utils.getAtString(qq)) || receivedGroupMessage.getRaw_message().equals(CQ_Generator_Utils.getAtString(qq) + " ")) {
            getRandomImage(receivedGroupMessage.getGroup_id());
        }
    }


    public void getRandomImage(int group_id) {
        ImageResponse imageResponse = groupImageService.getRandomImage();
        System.out.println(imageResponse.getUrl());
        if (imageResponse.getType() == 0) {
            gocqService.send_group_message(group_id, CQ_Generator_Utils.getImageString(imageResponse.getUrl()));
        } else {
            gocqService.send_group_message(group_id, CQ_Generator_Utils.getImageString(aliyunOSSUtils.getImageUrl(imageResponse.getUrl())));
        }
    }


    private String getRawPictureUrl(String raw_message) throws Exception {
        int messageid = CQ_String_Utils.getMessageId(raw_message);
        String replied_message = gocqService.get_message(messageid);
        List<String> raw_cq_string = CQ_String_Utils.getCQStrings(replied_message);
        return CQ_String_Utils.getImageURL(raw_cq_string.get(0));
    }

    private void handleImageActions(String raw_message, String raw_picture_url, int group_id, long poster) {
        if (raw_message.endsWith("删除图片")) {
            handleDeleteImage(raw_picture_url, group_id);
        } else if (raw_message.endsWith("恢复图片")) {
            handleRestoreImage(raw_picture_url, group_id);
        } else if (raw_message.endsWith("添加图片")) {
            handleAddImage(raw_picture_url, group_id, poster);
        }
    }

    private void handleDeleteImage(String raw_picture_url, int group_id) {
        ApiResponse<DeleteImageResponse> response = groupImageService.deleteImage(raw_picture_url);
        if (response.getCode() == ApiResultCode.SUCCESS.getCode() && response.getData().getCode() == DeleteImageResponseCode.IMAGE_DELETE_SUCCESS.getCode()) {
            gocqService.send_group_message(group_id, "已删除");
        } else {
            log.error(response.toString());
            gocqService.send_group_message(group_id, "找到原图片成功但，删除失败");
        }
    }

    private void handleRestoreImage(String raw_picture_url, int group_id) {
        ApiResponse<RestoreImageResponse> response = groupImageService.restoreImage(raw_picture_url);
        if (response.getCode() == ApiResultCode.SUCCESS.getCode() && response.getData().getCode() == RestoreImageResponseCode.IMAGE_RESTORE_SUCCESS.getCode()) {
            gocqService.send_group_message(group_id, "已恢复");
        } else {
            log.error(response.toString());
            gocqService.send_group_message(group_id, "找到原图片成功但，恢复失败");
        }
    }

    private void handleAddImage(String raw_picture_url, int group_id, long poster) {
        ApiResponse<CheckImageResponse> response = groupImageService.checkUrl(raw_picture_url, poster, group_id);
        if (response.getCode() == ApiResultCode.SUCCESS.getCode() && response.getData().getCode() == CheckImageResponseCode.IMAGE_DOWNLOAD_SUCCESS.getCode()) {
            gocqService.send_group_message(group_id, "没见过，偷了");
        } else if (response.getCode() == ApiResultCode.SUCCESS.getCode() && response.getData().getCode() == CheckImageResponseCode.IMAGE_DOWNLOAD_FAILED.getCode()) {
            log.error(response.toString());
            gocqService.send_group_message(group_id, "没见过，但没偷成");
        } else if (response.getCode() == ApiResultCode.SUCCESS.getCode() && response.getData().getCode() == CheckImageResponseCode.IMAGE_IN_DATABASE.getCode()) {
            log.info(response.toString());
            gocqService.send_group_message(group_id, "已经有了");
        }
    }
}

