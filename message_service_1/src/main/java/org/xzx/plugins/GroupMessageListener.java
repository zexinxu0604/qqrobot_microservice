package org.xzx.plugins;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.xzx.annotation.RobotListener;
import org.xzx.annotation.RobotListenerHandler;
import org.xzx.clients.ImageClient;
import org.xzx.clients.Jx3Clients;
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

    @RobotListenerHandler
    public void random(Received_Group_Message message) {
        if (message.getRaw_message().equals(CQ_Utils.getAtString(qq)) || message.getRaw_message().equals(CQ_Utils.getAtString(qq) + " ")) {
            System.out.println(imageClient.getRandomImage());
        }
    }

    @RobotListenerHandler(concurrency = true)
    public void getBaiZhan(Received_Group_Message message) {
        if (message.getRaw_message().equals("百战")) {
            if (jx3Clients.getBaizhan().equals("success")) {
                gocqService.send_group_message(message.getGroup_id(), CQ_Utils.getImageString("baizhan.png"));
            }
        }
    }

    @RobotListenerHandler(order = 0)
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

}
