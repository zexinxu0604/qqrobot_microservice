package org.xzx.plugins;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.xzx.annotation.RobotListener;
import org.xzx.annotation.RobotListenerHandler;
import org.xzx.bean.enums.CheckImageResponseCode;
import org.xzx.bean.enums.DeleteImageResponseCode;
import org.xzx.bean.enums.RestoreImageResponseCode;
import org.xzx.clients.ImageClient;
import org.xzx.clients.Jx3Clients;
import org.xzx.bean.response.ImageResponse;
import org.xzx.bean.messageBean.ReceivedGroupMessage;
import org.xzx.service.Gocq_service;
import org.xzx.utils.CQ_Utils;
import org.xzx.utils.Image_String_Utils;

import java.util.List;


//TODO 设置功能在群聊里的权限
@RobotListener
public class GroupMessageListener {

    @Resource
    private ImageClient imageClient;

    @Resource
    private Jx3Clients jx3Clients;

    @Autowired
    private Gocq_service gocqService;

    @Value("${qq.number}")
    private long qq;

    @RobotListenerHandler(concurrency = true)
    public void getBaiZhan(ReceivedGroupMessage message) {
        if (message.getRaw_message().equals("百战")) {
            if (jx3Clients.getBaizhan().equals("success")) {
                gocqService.send_group_message(message.getGroup_id(), CQ_Utils.getImageString("baizhan.png"));
            }
        }
    }

    @RobotListenerHandler
    public void checkImage(ReceivedGroupMessage receivedGroupMessage) {
        if (receivedGroupMessage.getRaw_message().startsWith("[CQ:image,")) {
            List<String> cqStrings = Image_String_Utils.getCQStrings(receivedGroupMessage.getRaw_message());
            String imagecq = cqStrings.get(0);
            long poster = receivedGroupMessage.getUser_id();
            long group_id = receivedGroupMessage.getGroup_id();
            if (Image_String_Utils.ifStaredImage(imagecq)) {
                String imageUrl = Image_String_Utils.getImageURL(imagecq);
                if (imageClient.checkUrl(imageUrl, poster, group_id).getCode() == CheckImageResponseCode.IMAGE_DOWNLOAD_SUCCESS.getCode()) {
                    gocqService.send_group_message(receivedGroupMessage.getGroup_id(), "没见过，偷了");
                } else if (imageClient.checkUrl(imageUrl, poster, group_id).getCode() == CheckImageResponseCode.IMAGE_DOWNLOAD_FAILED.getCode()) {
                    gocqService.send_group_message(receivedGroupMessage.getGroup_id(), "没见过，但没偷成");
                }
            }
        }
    }

    /**
     * @param receivedGroupMessage [CQ:reply,id=-635735050][CQ:at,qq=2351200988] [CQ:at,qq=2351200988] 123
     */
    @RobotListenerHandler
    public void deleteImage(ReceivedGroupMessage receivedGroupMessage) {
        int group_id = receivedGroupMessage.getGroup_id();
        if (receivedGroupMessage.getRaw_message().startsWith("[CQ:reply,") && receivedGroupMessage.getRaw_message().endsWith("删除图片")) {
            List<String> cqStrings = Image_String_Utils.getCQStrings(receivedGroupMessage.getRaw_message());
            String atPerson = cqStrings.get(1);
            long atQQ = Long.parseLong(Image_String_Utils.getQQFromAt(atPerson));
            if (atQQ == qq) {
                String replycq = cqStrings.get(0);
                int messageid = Integer.parseInt(Image_String_Utils.getIdFromReply(replycq));
                try {
                    JsonNode jsonNode = gocqService.get_message(messageid);
                    String raw_message = jsonNode.get("data").get("message").asText();
                    List<String> raw_cq_string = Image_String_Utils.getCQStrings(raw_message);
                    String raw_picture_url = Image_String_Utils.getImageURL(raw_cq_string.get(0));
                    if (imageClient.deleteImage(raw_picture_url).getCode() == DeleteImageResponseCode.IMAGE_DELETE_SUCCESS.getCode()) {
                        gocqService.send_group_message(group_id, "已删除");
                    } else {
                        gocqService.send_group_message(group_id, "找到原图片成功但，删除失败");
                    }
                } catch (Exception e) {
                    gocqService.send_group_message(group_id, "删除失败");
                }

            }
        }
    }

    @RobotListenerHandler
    public void restoreImage(ReceivedGroupMessage receivedGroupMessage) {
        int group_id = receivedGroupMessage.getGroup_id();
        if (receivedGroupMessage.getRaw_message().startsWith("[CQ:reply,") && receivedGroupMessage.getRaw_message().endsWith("恢复图片")) {
            List<String> cqStrings = Image_String_Utils.getCQStrings(receivedGroupMessage.getRaw_message());
            String atPerson = cqStrings.get(1);
            long atQQ = Long.parseLong(Image_String_Utils.getQQFromAt(atPerson));
            if (atQQ == qq) {
                String replycq = cqStrings.get(0);
                int messageid = Integer.parseInt(Image_String_Utils.getIdFromReply(replycq));
                try {
                    JsonNode jsonNode = gocqService.get_message(messageid);
                    String raw_message = jsonNode.get("data").get("message").asText();
                    List<String> raw_cq_string = Image_String_Utils.getCQStrings(raw_message);
                    String raw_picture_url = Image_String_Utils.getImageURL(raw_cq_string.get(0));
                    if (imageClient.restoreImage(raw_picture_url).getCode() == RestoreImageResponseCode.IMAGE_RESTORE_SUCCESS.getCode()) {
                        gocqService.send_group_message(group_id, "已恢复");
                    } else {
                        gocqService.send_group_message(group_id, "找到原图片成功但，恢复失败");
                    }
                } catch (Exception e) {
                    gocqService.send_group_message(group_id, "恢复失败");
                }

            }
        }
    }

    @RobotListenerHandler
    public void getRandomImage(ReceivedGroupMessage receivedGroupMessage) {
        if (receivedGroupMessage.getRaw_message().equals(CQ_Utils.getAtString(qq)) || receivedGroupMessage.getRaw_message().equals(CQ_Utils.getAtString(qq) + " ")) {
            ImageResponse imageResponse = imageClient.getRandomImage();
            System.out.println(imageResponse.getUrl());
            if(imageResponse.getType() == 0){
                gocqService.send_group_message(receivedGroupMessage.getGroup_id(), CQ_Utils.getImageString(imageResponse.getUrl()));
            } else {
                gocqService.send_group_message(receivedGroupMessage.getGroup_id(), CQ_Utils.getImageString(CQ_Utils.getlocalImageUrl(imageResponse.getUrl())));
            }
        }
    }
}

