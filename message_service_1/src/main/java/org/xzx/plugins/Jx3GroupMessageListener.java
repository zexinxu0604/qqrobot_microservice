package org.xzx.plugins;

import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.xzx.annotation.RobotListener;
import org.xzx.annotation.RobotListenerHandler;
import org.xzx.bean.Jx3Response.Jx3PictureUrlResponse;
import org.xzx.bean.enums.MessageBreakCode;
import org.xzx.bean.messageBean.ReceivedGroupMessage;
import org.xzx.bean.messageUtil.MessageBreaker;
import org.xzx.bean.messageUtil.MessageCounter;
import org.xzx.service.Gocq_service;
import org.xzx.service.GroupImageService;
import org.xzx.service.Jx3_service;
import org.xzx.utils.AliyunOSSUtils;
import org.xzx.utils.CQ_Generator_Utils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@RobotListener
@Log4j2
public class Jx3GroupMessageListener {

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

    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true)
    public void getJx3RoleDetatilPicture(ReceivedGroupMessage receivedGroupMessage) {
        if (receivedGroupMessage.getRaw_message().startsWith("查询 ")) {
            int group_id = receivedGroupMessage.getGroup_id();
            String raw_message = receivedGroupMessage.getRaw_message();
            String[] parts = raw_message.split(" ");
            Jx3PictureUrlResponse jx3PictureUrlResponse = null;
            if (parts.length == 2) {
                jx3PictureUrlResponse = jx3Service.get_role_info_picture(parts[1]);
            } else if (parts.length == 3) {
                jx3PictureUrlResponse = jx3Service.get_role_info_picture(parts[2], parts[1]);
            }
            catchJx3PictureResponseAndReturn(group_id, jx3PictureUrlResponse);
        }
    }

    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true)
    public void getJx3TeamRecruitPicture(ReceivedGroupMessage receivedGroupMessage) {
        if (receivedGroupMessage.getRaw_message().startsWith("招募 ")) {
            int group_id = receivedGroupMessage.getGroup_id();
            String raw_message = receivedGroupMessage.getRaw_message();
            String[] parts = raw_message.split(" ");
            Jx3PictureUrlResponse jx3PictureUrlResponse = null;
            if (parts.length == 2) {
                jx3PictureUrlResponse = jx3Service.get_team_recruit_picture(parts[1]);
            } else if (parts.length == 3) {
                jx3PictureUrlResponse = jx3Service.get_team_recruit_picture(parts[2], parts[1]);
            }

            catchJx3PictureResponseAndReturn(group_id, jx3PictureUrlResponse);
        }
    }

    private void catchJx3PictureResponseAndReturn(int group_id, Jx3PictureUrlResponse jx3PictureUrlResponse) {
        if (jx3PictureUrlResponse == null) {
            gocqService.send_group_message(group_id, "查询失败");
            return;
        }

        if (jx3PictureUrlResponse.getCode() == 200) {
            gocqService.send_group_message(group_id, CQ_Generator_Utils.getImageString(jx3PictureUrlResponse.getData().get("url").asText()));
        } else {
            gocqService.send_group_message(group_id, jx3PictureUrlResponse.getMsg());
        }
    }
}
