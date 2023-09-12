package org.xzx.plugins;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.xzx.annotation.RobotListener;
import org.xzx.annotation.RobotListenerHandler;
import org.xzx.clients.ImageClient;
import org.xzx.clients.Jx3Clients;
import org.xzx.pojo.Image.ImageResponse;
import org.xzx.pojo.messageBean.Received_Group_Message;
import org.xzx.service.Gocq_service;
import org.xzx.utils.CQ_Utils;
import org.xzx.utils.String_Utils;

import java.util.List;

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
    public void getBaiZhan(Received_Group_Message message) {
        if (message.getRaw_message().equals("百战")) {
            if (jx3Clients.getBaizhan().equals("success")) {
                gocqService.send_group_message(message.getGroup_id(), CQ_Utils.getImageString("baizhan.png"));
            }
        }
    }

    @RobotListenerHandler
    public void checkImage(Received_Group_Message receivedGroupMessage) {
        if (receivedGroupMessage.getRaw_message().startsWith("[CQ:image,")) {
            List<String> cqStrings = String_Utils.getCQStrings(receivedGroupMessage.getRaw_message());
            String imagecq = cqStrings.get(0);
            if (String_Utils.getImageSubType(imagecq).equals("1")) {
                String imageUrl = String_Utils.getImageURL(imagecq);
                if (imageClient.checkUrl(imageUrl) == 1) {
                    gocqService.send_group_message(receivedGroupMessage.getGroup_id(), "没见过，偷了");
                } else if (imageClient.checkUrl(imageUrl) == 2) {
                    gocqService.send_group_message(receivedGroupMessage.getGroup_id(), "没见过，但没偷成");
                }
            }
        }
    }

    /**
     * @param receivedGroupMessage [CQ:reply,id=-635735050][CQ:at,qq=2351200988] [CQ:at,qq=2351200988] 123
     */
    @RobotListenerHandler
    public void deleteImage(Received_Group_Message receivedGroupMessage) {
        int group_id = receivedGroupMessage.getGroup_id();
        if (receivedGroupMessage.getRaw_message().startsWith("[CQ:reply,") && receivedGroupMessage.getRaw_message().endsWith("删除图片")) {
            List<String> cqStrings = String_Utils.getCQStrings(receivedGroupMessage.getRaw_message());
            String atPerson = cqStrings.get(1);
            long atQQ = Long.parseLong(String_Utils.getQQFromAt(atPerson));
            if (atQQ == qq) {
                String replycq = cqStrings.get(0);
                int messageid = Integer.parseInt(String_Utils.getIdFromReply(replycq));
                try {
                    JsonNode jsonNode = gocqService.get_message(messageid);
                    String raw_message = jsonNode.get("data").get("message").asText();
                    List<String> raw_cq_string = String_Utils.getCQStrings(raw_message);
                    String raw_picture_url = String_Utils.getImageURL(raw_cq_string.get(0));
                    if (imageClient.deleteImage(raw_picture_url)) {
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
    public void restoreImage(Received_Group_Message receivedGroupMessage) {
        int group_id = receivedGroupMessage.getGroup_id();
        if (receivedGroupMessage.getRaw_message().startsWith("[CQ:reply,") && receivedGroupMessage.getRaw_message().endsWith("恢复图片")) {
            List<String> cqStrings = String_Utils.getCQStrings(receivedGroupMessage.getRaw_message());
            String atPerson = cqStrings.get(1);
            long atQQ = Long.parseLong(String_Utils.getQQFromAt(atPerson));
            if (atQQ == qq) {
                String replycq = cqStrings.get(0);
                int messageid = Integer.parseInt(String_Utils.getIdFromReply(replycq));
                try {
                    JsonNode jsonNode = gocqService.get_message(messageid);
                    String raw_message = jsonNode.get("data").get("message").asText();
                    List<String> raw_cq_string = String_Utils.getCQStrings(raw_message);
                    String raw_picture_url = String_Utils.getImageURL(raw_cq_string.get(0));
                    if (imageClient.restoreImage(raw_picture_url)) {
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
    public void getRandomImage(Received_Group_Message receivedGroupMessage) {
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

