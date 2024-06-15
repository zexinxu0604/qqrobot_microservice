package org.xzx.plugins;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.xzx.annotation.RobotListener;
import org.xzx.annotation.RobotListenerHandler;
import org.xzx.bean.Jx3.Jx3Response.Jx3PictureUrlResponse;
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
    private Map<Long, MessageCounter> messageCounterMap;


    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true)
    public void getJx3RoleDetatilPicture(ReceivedGroupMessage receivedGroupMessage) {
        if (receivedGroupMessage.getRaw_message().startsWith("属性 ")) {
            long group_id = receivedGroupMessage.getGroup_id();
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
    public void getJx3RoleFireworkPicture(ReceivedGroupMessage receivedGroupMessage) {
        if (receivedGroupMessage.getRaw_message().startsWith("烟花 ")) {
            long group_id = receivedGroupMessage.getGroup_id();
            String raw_message = receivedGroupMessage.getRaw_message();
            String[] parts = raw_message.split(" ");
            Jx3PictureUrlResponse jx3PictureUrlResponse = null;
            if (parts.length == 2) {
                jx3PictureUrlResponse = jx3Service.get_firework_picture(parts[1]);
            } else if (parts.length == 3) {
                jx3PictureUrlResponse = jx3Service.get_firework_picture(parts[2], parts[1]);
            }
            catchJx3PictureResponseAndReturn(group_id, jx3PictureUrlResponse);
        }
    }

    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true)
    public void getJx3RoleLuckPicture(ReceivedGroupMessage receivedGroupMessage) {
        if (receivedGroupMessage.getRaw_message().startsWith("查询 ")) {
            long group_id = receivedGroupMessage.getGroup_id();
            String raw_message = receivedGroupMessage.getRaw_message();
            String[] parts = raw_message.split(" ");
            Jx3PictureUrlResponse jx3PictureUrlResponse = null;
            if (parts.length == 2) {
                jx3PictureUrlResponse = jx3Service.get_role_luck_picture(parts[1]);
            } else if (parts.length == 3) {
                jx3PictureUrlResponse = jx3Service.get_role_luck_picture(parts[2], parts[1]);
            }
            catchJx3PictureResponseAndReturn(group_id, jx3PictureUrlResponse);
        }
    }

    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true)
    public void getJx3TeamRecruitPicture(ReceivedGroupMessage receivedGroupMessage) {
        if (receivedGroupMessage.getRaw_message().startsWith("招募 ")) {
            long group_id = receivedGroupMessage.getGroup_id();
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

    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true)
    public void getJx3BaizhanBossPicture(ReceivedGroupMessage receivedGroupMessage) {
        if (receivedGroupMessage.getRaw_message().equals("百战")) {
            long group_id = receivedGroupMessage.getGroup_id();
            Jx3PictureUrlResponse jx3PictureUrlResponse = jx3Service.get_baizhan_boss_picture();
            catchJx3PictureResponseAndReturn(group_id, jx3PictureUrlResponse);
        }
    }

    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true)
    public void getJx3BossTreasurePicture(ReceivedGroupMessage receivedGroupMessage) {
        if (receivedGroupMessage.getRaw_message().startsWith("掉落 ")) {
            long group_id = receivedGroupMessage.getGroup_id();
            String raw_message = receivedGroupMessage.getRaw_message();
            String[] parts = raw_message.split(" ");
            Jx3PictureUrlResponse jx3PictureUrlResponse = null;
            if (parts.length == 2) {
                jx3PictureUrlResponse = jx3Service.get_boss_treasure_picture(parts[1]);
            } else if (parts.length == 3) {
                jx3PictureUrlResponse = jx3Service.get_boss_treasure_picture(parts[2], parts[1]);
            }

            catchJx3PictureResponseAndReturn(group_id, jx3PictureUrlResponse);
        }
    }

    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true)
    public void getJx3BlackTradePicture(ReceivedGroupMessage receivedGroupMessage) {
        if (receivedGroupMessage.getRaw_message().startsWith("价格 ")) {
            long group_id = receivedGroupMessage.getGroup_id();
            String raw_message = receivedGroupMessage.getRaw_message();
            String[] parts = raw_message.split(" ");
            Jx3PictureUrlResponse jx3PictureUrlResponse = null;
            jx3PictureUrlResponse = jx3Service.get_black_trade_picture(parts[1]);
            catchJx3PictureResponseAndReturn(group_id, jx3PictureUrlResponse);
        }
    }

    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true)
    public void getJx3DailyPicture(ReceivedGroupMessage receivedGroupMessage) {
        String raw_message = receivedGroupMessage.getRaw_message();
        long group_id = receivedGroupMessage.getGroup_id();
        if (raw_message.equals("日常")) {
            Jx3PictureUrlResponse jx3PictureUrlResponse = jx3Service.get_daily_picture();
            catchJx3PictureResponseAndReturn(group_id, jx3PictureUrlResponse);
            return;
        }

        if (raw_message.startsWith("日常 ")) {
            String[] parts = raw_message.split(" ");
            Jx3PictureUrlResponse jx3PictureUrlResponse = null;
            if (parts.length == 2) {
                jx3PictureUrlResponse = jx3Service.get_daily_picture(parts[1]);
                catchJx3PictureResponseAndReturn(group_id, jx3PictureUrlResponse);
                return;
            }
        }
    }

    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true)
    public void getJx3GoldPrice(ReceivedGroupMessage receivedGroupMessage) {
        String raw_message = receivedGroupMessage.getRaw_message();
        long group_id = receivedGroupMessage.getGroup_id();
        if (raw_message.equals("金价")) {
            Jx3PictureUrlResponse jx3PictureUrlResponse = jx3Service.get_gold_price();
            catchJx3PictureResponseAndReturn(group_id, jx3PictureUrlResponse);
            return;
        }

        if (raw_message.startsWith("金价 ")) {
            String[] parts = raw_message.split(" ");
            Jx3PictureUrlResponse jx3PictureUrlResponse = null;
            if (parts.length == 2) {
                jx3PictureUrlResponse = jx3Service.get_gold_price(parts[1]);
                catchJx3PictureResponseAndReturn(group_id, jx3PictureUrlResponse);
                return;
            }
        }
    }

    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true)
    public void getServerStatus(ReceivedGroupMessage receivedGroupMessage) {
        if (receivedGroupMessage.getRaw_message().equals("开服")) {
            long group_id = receivedGroupMessage.getGroup_id();
            Boolean status = jx3Service.get_server_open_status();
            if (status){
                gocqService.send_group_message(group_id, "破阵子,已开服");
            } else {
                gocqService.send_group_message(group_id, "破阵子,未开服");
            }
        }

        if (receivedGroupMessage.getRaw_message().startsWith("开服 ")) {
            long group_id = receivedGroupMessage.getGroup_id();
            String[] parts = receivedGroupMessage.getRaw_message().split(" ");
            Boolean status = jx3Service.get_server_open_status(parts[1]);
            if (status){
                gocqService.send_group_message(group_id, parts[1] + ",已开服");
            } else {
                gocqService.send_group_message(group_id, parts[1] + ",未开服");
            }
        }
    }


    private void catchJx3PictureResponseAndReturn(long group_id, Jx3PictureUrlResponse jx3PictureUrlResponse) {
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
