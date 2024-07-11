package org.xzx.plugins;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.xzx.annotation.RobotListener;
import org.xzx.annotation.RobotListenerHandler;
import org.xzx.bean.Jx3.Jx3Response.Jx3PictureUrlResponse;
import org.xzx.bean.enums.GroupServiceEnum;
import org.xzx.bean.messageBean.ReceivedGroupMessage;
import org.xzx.bean.messageUtil.MessageCounter;
import org.xzx.bean.qqGroupBean.GroupService;
import org.xzx.service.Gocq_service;
import org.xzx.service.GroupImageService;
import org.xzx.service.GroupServiceService;
import org.xzx.service.Jx3_service;
import org.xzx.utils.AliyunOSSUtils;
import org.xzx.utils.CQ_Generator_Utils;
import org.xzx.utils.CQ_String_Utils;

import java.util.Map;

@RobotListener
@Log4j2
@RefreshScope
public class Jx3GroupMessageListener {

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

    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;

    @Autowired
    private Map<Long, MessageCounter> messageCounterMap;


    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true, regex = "^属性 .*$")
    public void getJx3RoleDetatilPicture(ReceivedGroupMessage receivedGroupMessage) {
        if(!groupServiceService.checkServiceStatus(receivedGroupMessage.getGroup_id(), GroupServiceEnum.JX3_ATTRIBUTE)){
            return;
        }
        long group_id = receivedGroupMessage.getGroup_id();
        String raw_message = receivedGroupMessage.getRaw_message();
        String[] parts = raw_message.split(" ");
        Jx3PictureUrlResponse jx3PictureUrlResponse = null;
        if (parts.length == 2) {
            jx3PictureUrlResponse = jx3Service.get_role_info_picture(parts[1]);
        } else if (parts.length == 3) {
            jx3PictureUrlResponse = jx3Service.get_role_info_picture(parts[2], parts[1]);
        } else {
            gocqService.send_group_message(group_id, "请检查格式是否为 属性 角色名 或者 属性 服务器 角色名");
            return;
        }
        catchJx3PictureResponseAndReturn(group_id, jx3PictureUrlResponse);
    }

    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true, regex = "^烟花 .*$")
    public void getJx3RoleFireworkPicture(ReceivedGroupMessage receivedGroupMessage) {
        if(!groupServiceService.checkServiceStatus(receivedGroupMessage.getGroup_id(), GroupServiceEnum.JX3_FIREWORK)){
            return;
        }
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

    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true, regex = "^查询 .*$")
    public void getJx3RoleLuckPicture(ReceivedGroupMessage receivedGroupMessage) {
        if(!groupServiceService.checkServiceStatus(receivedGroupMessage.getGroup_id(), GroupServiceEnum.JX3_LUCK)){
            return;
        }
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

    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true, regex = "^招募 .*$")
    public void getJx3TeamRecruitPicture(ReceivedGroupMessage receivedGroupMessage) {
        if(!groupServiceService.checkServiceStatus(receivedGroupMessage.getGroup_id(), GroupServiceEnum.JX3_TEAM_RECRUIT)){
            return;
        }
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

    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true, regex = "百战", isFullMatch = true)
    public void getJx3BaizhanBossPicture(ReceivedGroupMessage receivedGroupMessage) {
        if(!groupServiceService.checkServiceStatus(receivedGroupMessage.getGroup_id(), GroupServiceEnum.JX3_BaiZhan)){
            return;
        }
        long group_id = receivedGroupMessage.getGroup_id();
        Jx3PictureUrlResponse jx3PictureUrlResponse = jx3Service.get_baizhan_boss_picture();
        catchJx3PictureResponseAndReturn(group_id, jx3PictureUrlResponse);
    }

    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true, regex = "^掉落 .*$")
    public void getJx3BossTreasurePicture(ReceivedGroupMessage receivedGroupMessage) {
        if(!groupServiceService.checkServiceStatus(receivedGroupMessage.getGroup_id(), GroupServiceEnum.JX3_BOSS_TREASURE)){
            return;
        }
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

    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true, regex = "^物价 .*$")
    public void getJx3BlackTradePicture(ReceivedGroupMessage receivedGroupMessage) {
        if(!groupServiceService.checkServiceStatus(receivedGroupMessage.getGroup_id(), GroupServiceEnum.JX3_BLACK_TRADE)){
            return;
        }
        long group_id = receivedGroupMessage.getGroup_id();
        String raw_message = receivedGroupMessage.getRaw_message();
        String[] parts = raw_message.split(" ");
        Jx3PictureUrlResponse jx3PictureUrlResponse = null;
        jx3PictureUrlResponse = jx3Service.get_black_trade_picture(parts[1]);
        catchJx3PictureResponseAndReturn(group_id, jx3PictureUrlResponse);
    }

    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true, regex = "^日常.*$")
    public void getJx3DailyPicture(ReceivedGroupMessage receivedGroupMessage) {
        if(!groupServiceService.checkServiceStatus(receivedGroupMessage.getGroup_id(), GroupServiceEnum.JX3_DAILY)){
            return;
        }
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

    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true, regex = "^金价.*$")
    public void getJx3GoldPrice(ReceivedGroupMessage receivedGroupMessage) {
        if(!groupServiceService.checkServiceStatus(receivedGroupMessage.getGroup_id(), GroupServiceEnum.JX3_GOLD_PRICE)){
            return;
        }
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

    @RobotListenerHandler(order = 0, shutdown = true, concurrency = true, regex = "^开服.*$")
    public void getServerStatus(ReceivedGroupMessage receivedGroupMessage) {
        if(!groupServiceService.checkServiceStatus(receivedGroupMessage.getGroup_id(), GroupServiceEnum.SERVER_OPEN)){
            return;
        }
        if (receivedGroupMessage.getRaw_message().equals("开服")) {
            long group_id = receivedGroupMessage.getGroup_id();
            Boolean status = jx3Service.get_server_open_status();
            if (status) {
                gocqService.send_group_message(group_id, "破阵子,已开服");
            } else {
                gocqService.send_group_message(group_id, "破阵子,未开服");
            }
        }

        if (receivedGroupMessage.getRaw_message().startsWith("开服 ")) {
            long group_id = receivedGroupMessage.getGroup_id();
            String[] parts = receivedGroupMessage.getRaw_message().split(" ");
            Boolean status = jx3Service.get_server_open_status(parts[1]);
            if (status) {
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
