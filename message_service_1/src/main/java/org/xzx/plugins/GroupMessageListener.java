package org.xzx.plugins;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.xzx.annotation.RobotListener;
import org.xzx.annotation.RobotListenerHandler;
import org.xzx.bean.Domain.OffWorkRecord;
import org.xzx.bean.ImageBean.ImageCQ;
import org.xzx.bean.enums.*;
import org.xzx.bean.messageBean.ReceivedGroupMessage;
import org.xzx.bean.messageUtil.MessageCounter;
import org.xzx.bean.qqGroupBean.GroupService;
import org.xzx.bean.response.*;
import org.xzx.service.*;
import org.xzx.utils.AliyunOSSUtils;
import org.xzx.utils.CQ_Generator_Utils;
import org.xzx.utils.CQ_String_Utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;


//TODO 设置功能在群聊里的权限
@RobotListener
@Log4j2
@RefreshScope
public class GroupMessageListener {


    @Autowired
    private Gocq_service gocqService;

    @Autowired
    private GroupImageService groupImageService;

    @Autowired
    private Jx3_service jx3Service;

    @Autowired
    private GroupServiceService groupServiceService;

    @Value("${qq.number}")
    private long qq;

    @Value("${qq.group.imageSaveSize}")
    private int imagesize;

    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;

    @Autowired
    private ChatAIService chatAIService;

    @Autowired
    private Map<Long, MessageCounter> messageCounterMap;

    @Autowired
    private OffWorkRecordService offWorkRecordService;


    @RobotListenerHandler(order = -1, isAllRegex = true)
    public void countMessage(ReceivedGroupMessage receivedGroupMessage) {
        long group_id = receivedGroupMessage.getGroup_id();
        MessageCounter messageCounter = messageCounterMap.get(group_id);
        messageCounter.addMessageCount();
        if (messageCounter.getMessageCount() == messageCounter.getMaxMessageCount()) {
            getRandomImage(group_id);
            messageCounter.setMessageCount(0);
        }
    }

    /**
     * Check if the received group message contains an image and process it accordingly.
     *
     * @param receivedGroupMessage the received group message object
     */
    @RobotListenerHandler(order = 1, shutdown = true, regex = "^\\[CQ:image,[^\\]]*\\]$")
    public void checkImage(ReceivedGroupMessage receivedGroupMessage) {
        if(!groupServiceService.checkServiceStatus(receivedGroupMessage.getGroup_id(), GroupServiceEnum.RANDOM_PICTURE)){
            return;
        }
        List<String> cqStrings = CQ_String_Utils.getCQStrings(receivedGroupMessage.getRaw_message());
        String imagecq = cqStrings.get(0);
        String imageFileName = CQ_String_Utils.getImageFileName(imagecq);
        ApiResponse<CheckImageResponse> checkImageResponseApiResponse = groupImageService.checkImageFileName(imageFileName);
        if (checkImageResponseApiResponse.getCode() == ApiResultCode.FAILED.getCode() && CQ_String_Utils.getImageFileSize(imagecq) < imagesize) {
            ImageCQ imageCQ = new ImageCQ();
            imageCQ.setUrl(CQ_String_Utils.getImageURL(imagecq));
            imageCQ.setGroup_id(receivedGroupMessage.getGroup_id());
            imageCQ.setPoster(receivedGroupMessage.getUser_id());
            imageCQ.setFile_name(imageFileName);
            imageCQ.setFile_size(CQ_String_Utils.getImageFileSize(imagecq));
            ApiResponse<CheckImageResponse> response = groupImageService.insertImage(imageCQ);
            if (response.getCode() == ApiResultCode.SUCCESS.getCode() && response.getData().getCode() == CheckImageResponseCode.IMAGE_DOWNLOAD_SUCCESS.getCode()) {
                gocqService.send_group_message(receivedGroupMessage.getGroup_id(), "没见过，偷了");
            } else {
                log.error(response.toString());
                gocqService.send_group_message(receivedGroupMessage.getGroup_id(), "没偷成");
            }
        }
    }

    /**
     * @param receivedGroupMessage [CQ:reply,id=-635735050][CQ:at,qq=2351200988] [CQ:at,qq=2351200988] 123
     */
    @RobotListenerHandler(order = 1, shutdown = true, regex = "^\\[CQ:reply,.*?图片$")
    public void imageReplyActions(ReceivedGroupMessage receivedGroupMessage) {
        long group_id = receivedGroupMessage.getGroup_id();
        long poster = receivedGroupMessage.getUser_id();
        String raw_message = receivedGroupMessage.getRaw_message();
        if (!raw_message.startsWith("[CQ:reply,") || !raw_message.endsWith("图片")) {
            return;
        }
        try {
            String raw_picture_cq = getRawPictureCQ(raw_message);
            ImageCQ imageCQ = new ImageCQ();
            imageCQ.setUrl(CQ_String_Utils.getImageURL(raw_picture_cq));
            imageCQ.setFile_size(CQ_String_Utils.getImageFileSize(raw_picture_cq));
            imageCQ.setFile_name(CQ_String_Utils.getImageFileName(raw_picture_cq));
            imageCQ.setGroup_id(group_id);
            imageCQ.setPoster(poster);
            handleImageActions(raw_message, imageCQ);
        } catch (Exception e) {
            log.error("在处理回复消息中出现问题", e);
        }
    }


    @RobotListenerHandler(order = 1, concurrency = true, isAllRegex = true)
    public void getRandomImage(ReceivedGroupMessage receivedGroupMessage) {
        log.info(receivedGroupMessage.getRaw_message());
        if (receivedGroupMessage.getRaw_message().equals(CQ_Generator_Utils.getAtString(qq)) || receivedGroupMessage.getRaw_message().equals(CQ_Generator_Utils.getAtString(qq) + " ")) {
            if(!groupServiceService.checkServiceStatus(receivedGroupMessage.getGroup_id(), GroupServiceEnum.AT_RANDOM_PICTURE)){
                return;
            }
            getRandomImage(receivedGroupMessage.getGroup_id());
        }
    }

    @RobotListenerHandler(order = 2, concurrency = true, isAllRegex = true)
    public void getAIResponse(ReceivedGroupMessage receivedGroupMessage) {
        if (receivedGroupMessage.getRaw_message().startsWith(CQ_Generator_Utils.getAtString(qq)) && !CQ_Generator_Utils.getAtString(qq).equals(receivedGroupMessage.getRaw_message()) && !CQ_Generator_Utils.getAtString(qq).equals(receivedGroupMessage.getRaw_message() + " ")){
            if(!groupServiceService.checkServiceStatus(receivedGroupMessage.getGroup_id(), GroupServiceEnum.GPT_CHAT)){
                return;
            }
            String message = receivedGroupMessage.getRaw_message().replace(CQ_Generator_Utils.getAtString(qq), "");
            String response = chatAIService.getChatAIResponse(message);
            gocqService.send_group_message(receivedGroupMessage.getGroup_id(), response);
        }
    }

    @RobotListenerHandler(order = 0, concurrency = true, regex = "^下班$|^下班.$")
    public void offWorkRecord(ReceivedGroupMessage receivedGroupMessage) {
        if(!groupServiceService.checkServiceStatus(receivedGroupMessage.getGroup_id(), GroupServiceEnum.OFF_WORK_RECORD)){
            return;
        }
        LocalDate localDate = LocalDate.now();
        OffWorkRecord offWorkRecord;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        offWorkRecord = offWorkRecordService.selectOffWorkRecordByGroupIdAndMemberIdAndOffworkDay(receivedGroupMessage.getGroup_id(), receivedGroupMessage.getUser_id(), localDate);
        if (offWorkRecord == null) {
            Date date = new Date();
            offWorkRecord = new OffWorkRecord(receivedGroupMessage.getGroup_id(), receivedGroupMessage.getUser_id(), localDate, date);
            if(offWorkRecordService.insertOffWorkRecord(offWorkRecord)){
                gocqService.send_group_message(receivedGroupMessage.getGroup_id(), "打卡成功！打卡时间为：" + sdf.format(offWorkRecord.getOffwork_time()));
            } else {
                gocqService.send_group_message(receivedGroupMessage.getGroup_id(), "打卡失败！");
            }
        } else {
            offWorkRecord.setOffwork_time(new Date());
            if(offWorkRecordService.updateOffWorkRecordByGroupIdAndMemberIdAndOffworkDay(receivedGroupMessage.getGroup_id(), receivedGroupMessage.getUser_id(), localDate, offWorkRecord.getOffwork_time())){
                gocqService.send_group_message(receivedGroupMessage.getGroup_id(), "打卡成功！打卡时间为：" + sdf.format(offWorkRecord.getOffwork_time()));
            } else {
                gocqService.send_group_message(receivedGroupMessage.getGroup_id(), "打卡失败！");
            }
        }
    }

    @RobotListenerHandler(order = 0, regex = "^(功能|功能列表)$")
    public void getGroupServiceList(ReceivedGroupMessage receivedGroupMessage) {
        StringBuilder stringBuilder = new StringBuilder();
        for (GroupServiceEnum groupServiceEnum : GroupServiceEnum.values()) {
            stringBuilder.append(groupServiceEnum.getServiceDesc()).append("：").append(groupServiceEnum.getServiceTrigger()).append("\n");
        }
        gocqService.send_group_message(receivedGroupMessage.getGroup_id(), stringBuilder.toString());
    }


    public void getRandomImage(long group_id) {
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

    private String getRawPictureCQ(String raw_message) throws Exception {
        int messageid = CQ_String_Utils.getMessageId(raw_message);
        String replied_message = gocqService.get_message(messageid);
        List<String> raw_cq_string = CQ_String_Utils.getCQStrings(replied_message);
        return raw_cq_string.get(0);
    }

    private void handleImageActions(String raw_message, ImageCQ imageCQ) {
        if (raw_message.endsWith("删除图片")) {
            handleDeleteImage(imageCQ);
        } else if (raw_message.endsWith("恢复图片")) {
            handleAddImage(imageCQ);
        } else if (raw_message.endsWith("添加图片")) {
            handleAddImage(imageCQ);
        } else if (raw_message.endsWith("查看图片")) {
            gocqService.send_group_message(imageCQ.getGroup_id(), CQ_Generator_Utils.getImageString(imageCQ.getUrl()) + "\n" + imageCQ.getUrl());
        }
    }

    private void handleDeleteImage(ImageCQ imageCQ) {
        ApiResponse<DeleteImageResponse> response = groupImageService.realDeleteImage(imageCQ);
        if (response.getCode() == ApiResultCode.SUCCESS.getCode() && response.getData().getCode() == DeleteImageResponseCode.IMAGE_DELETE_SUCCESS.getCode()) {
            gocqService.send_group_message(imageCQ.getGroup_id(), "已删除");
        } else {
            log.error(response.toString());
            gocqService.send_group_message(imageCQ.getGroup_id(), "找到原图片成功但，删除失败");
        }
    }

    private void handleRestoreImage(ImageCQ imageCQ) {
        ApiResponse<RestoreImageResponse> response = groupImageService.restoreImage(imageCQ);
        if (response.getCode() == ApiResultCode.SUCCESS.getCode() && response.getData().getCode() == RestoreImageResponseCode.IMAGE_RESTORE_SUCCESS.getCode()) {
            gocqService.send_group_message(imageCQ.getGroup_id(), "已恢复");
        } else {
            log.error(response.toString());
            gocqService.send_group_message(imageCQ.getGroup_id(), "找到原图片成功但，恢复失败");
        }
    }

    private void handleAddImage(ImageCQ imageCQ) {
        ApiResponse<CheckImageResponse> response = groupImageService.checkImageFileName(imageCQ.getFile_name());
        if (response.getCode() == ApiResultCode.SUCCESS.getCode() && response.getData().getCode() == CheckImageResponseCode.IMAGE_IN_DATABASE.getCode()) {
            log.info(response.toString());
            gocqService.send_group_message(imageCQ.getGroup_id(), "已经有了");
        } else {
            response = groupImageService.insertImage(imageCQ);
            if (response.getCode() == ApiResultCode.SUCCESS.getCode() && response.getData().getCode() == CheckImageResponseCode.IMAGE_DOWNLOAD_SUCCESS.getCode()) {
                gocqService.send_group_message(imageCQ.getGroup_id(), "添加成功");
            } else {
                log.error(response.toString());
                gocqService.send_group_message(imageCQ.getGroup_id(), "添加失败");
            }
        }
    }
}

