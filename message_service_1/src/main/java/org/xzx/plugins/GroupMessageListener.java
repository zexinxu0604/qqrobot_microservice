package org.xzx.plugins;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.xzx.annotation.RobotListener;
import org.xzx.annotation.RobotListenerHandler;
import org.xzx.bean.enums.ApiResultCode;
import org.xzx.bean.enums.CheckImageResponseCode;
import org.xzx.bean.enums.DeleteImageResponseCode;
import org.xzx.bean.enums.RestoreImageResponseCode;
import org.xzx.bean.response.*;
import org.xzx.clients.ImageClient;
import org.xzx.clients.Jx3Clients;
import org.xzx.bean.messageBean.ReceivedGroupMessage;
import org.xzx.service.Gocq_service;
import org.xzx.service.GroupImageService;
import org.xzx.utils.AliyunOSSUtils;
import org.xzx.utils.CQ_Generator_Utils;
import org.xzx.utils.CQ_String_Utils;

import java.util.List;


//TODO 设置功能在群聊里的权限
@RobotListener
@Log4j2
public class GroupMessageListener {

    @Resource
    private ImageClient imageClient;

    @Resource
    private Jx3Clients jx3Clients;

    @Autowired
    private Gocq_service gocqService;

    @Autowired
    private GroupImageService groupImageService;

    @Value("${qq.number}")
    private long qq;

    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;

    @RobotListenerHandler(concurrency = true)
    public void getBaiZhan(ReceivedGroupMessage message) {
        if (message.getRaw_message().equals("百战")) {
            if (jx3Clients.getBaizhan().equals("success")) {
                gocqService.send_group_message(message.getGroup_id(), CQ_Generator_Utils.getImageString("baizhan.png"));
            }
        }
    }

    @RobotListenerHandler
    public void checkImage(ReceivedGroupMessage receivedGroupMessage) {
        if (receivedGroupMessage.getRaw_message().startsWith("[CQ:image,")) {
            List<String> cqStrings = CQ_String_Utils.getCQStrings(receivedGroupMessage.getRaw_message());
            String imagecq = cqStrings.get(0);
            long poster = receivedGroupMessage.getUser_id();
            long group_id = receivedGroupMessage.getGroup_id();
            if (CQ_String_Utils.ifStaredImage(imagecq)) {
                String imageUrl = CQ_String_Utils.getImageURL(imagecq);
                ApiResponse<CheckImageResponse> checkImageResponseApiResponse = imageClient.checkUrl(imageUrl, poster, group_id);
                if (checkImageResponseApiResponse.getCode() == ApiResultCode.SUCCESS.getCode() && checkImageResponseApiResponse.getData().getCode() == CheckImageResponseCode.IMAGE_DOWNLOAD_SUCCESS.getCode()) {
                    gocqService.send_group_message(receivedGroupMessage.getGroup_id(), "没见过，偷了");
                } else if (checkImageResponseApiResponse.getCode() == ApiResultCode.SUCCESS.getCode() && checkImageResponseApiResponse.getData().getCode() == CheckImageResponseCode.IMAGE_DOWNLOAD_FAILED.getCode()) {
                    gocqService.send_group_message(receivedGroupMessage.getGroup_id(), "没见过，但没偷成");
                }
            }
        }
    }

    /**
     * @param receivedGroupMessage [CQ:reply,id=-635735050][CQ:at,qq=2351200988] [CQ:at,qq=2351200988] 123
     */
    @RobotListenerHandler(shutdown = true)
    public void imageReplyActions(ReceivedGroupMessage receivedGroupMessage) {
        int group_id = receivedGroupMessage.getGroup_id();
        int poster = receivedGroupMessage.getUser_id();
        String raw_message = receivedGroupMessage.getRaw_message();
        if (raw_message.startsWith("[CQ:reply,") && raw_message.endsWith("图片")) {
            int messageid = CQ_String_Utils.getMessageId(raw_message);
            try {
                JsonNode jsonNode = gocqService.get_message(messageid);
                String replied_message = jsonNode.get("data").get("message").asText();
                List<String> raw_cq_string = CQ_String_Utils.getCQStrings(replied_message);
                String raw_picture_url = CQ_String_Utils.getImageURL(raw_cq_string.get(0));

                if (raw_message.endsWith("删除图片")) {
                    ApiResponse<DeleteImageResponse> deleteImageResponseApiResponse = groupImageService.deleteImage(raw_picture_url);
                    if (deleteImageResponseApiResponse.getCode() == ApiResultCode.SUCCESS.getCode() && deleteImageResponseApiResponse.getData().getCode() == DeleteImageResponseCode.IMAGE_DELETE_SUCCESS.getCode()) {
                        gocqService.send_group_message(group_id, "已删除");
                    } else {
                        log.error(deleteImageResponseApiResponse.toString());
                        gocqService.send_group_message(group_id, "找到原图片成功但，删除失败");
                    }
                } else if (raw_message.endsWith("恢复图片")) {
                    ApiResponse<RestoreImageResponse> restoreImageResponseApiResponse = groupImageService.restoreImage(raw_picture_url);
                    if (restoreImageResponseApiResponse.getCode() == ApiResultCode.SUCCESS.getCode() && restoreImageResponseApiResponse.getData().getCode() == RestoreImageResponseCode.IMAGE_RESTORE_SUCCESS.getCode()) {
                        gocqService.send_group_message(group_id, "已恢复");
                    } else {
                        log.error(restoreImageResponseApiResponse.toString());
                        gocqService.send_group_message(group_id, "找到原图片成功但，恢复失败");
                    }
                } else if (raw_message.endsWith("添加图片")) {
                    ApiResponse<CheckImageResponse> checkImageResponseApiResponse = imageClient.checkUrl(raw_picture_url, poster, group_id);
                    if (checkImageResponseApiResponse.getCode() == ApiResultCode.SUCCESS.getCode() && checkImageResponseApiResponse.getData().getCode() == CheckImageResponseCode.IMAGE_DOWNLOAD_SUCCESS.getCode()) {
                        gocqService.send_group_message(receivedGroupMessage.getGroup_id(), "没见过，偷了");
                    } else if (checkImageResponseApiResponse.getCode() == ApiResultCode.SUCCESS.getCode() && checkImageResponseApiResponse.getData().getCode() == CheckImageResponseCode.IMAGE_DOWNLOAD_FAILED.getCode()) {
                        log.error(checkImageResponseApiResponse.toString());
                        gocqService.send_group_message(receivedGroupMessage.getGroup_id(), "没见过，但没偷成");
                    } else if (checkImageResponseApiResponse.getCode() == ApiResultCode.SUCCESS.getCode() && checkImageResponseApiResponse.getData().getCode() == CheckImageResponseCode.IMAGE_IN_DATABASE.getCode()){
                        log.info(checkImageResponseApiResponse.toString());
                        gocqService.send_group_message(receivedGroupMessage.getGroup_id(), "已经有了");
                    }
                }
            } catch (Exception e) {
                log.error("在处理回复消息中出现问题", e);
            }
        }
    }


    @RobotListenerHandler
    public void getRandomImage(ReceivedGroupMessage receivedGroupMessage) {
        if (receivedGroupMessage.getRaw_message().equals(CQ_Generator_Utils.getAtString(qq)) || receivedGroupMessage.getRaw_message().equals(CQ_Generator_Utils.getAtString(qq) + " ")) {
            ImageResponse imageResponse = imageClient.getRandomImage();
            System.out.println(imageResponse.getUrl());
            if (imageResponse.getType() == 0) {
                gocqService.send_group_message(receivedGroupMessage.getGroup_id(), CQ_Generator_Utils.getImageString(imageResponse.getUrl()));
            } else {
                gocqService.send_group_message(receivedGroupMessage.getGroup_id(), CQ_Generator_Utils.getImageString(aliyunOSSUtils.getImageUrl(imageResponse.getUrl())));
            }
        }
    }
}

