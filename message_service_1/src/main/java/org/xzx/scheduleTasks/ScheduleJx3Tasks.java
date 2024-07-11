package org.xzx.scheduleTasks;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xzx.bean.enums.GroupServiceEnum;
import org.xzx.bean.qqGroupBean.GroupService;
import org.xzx.configs.Constants;
import org.xzx.service.Gocq_service;
import org.xzx.service.GroupServiceService;
import org.xzx.service.Jx3_service;

import java.util.List;

@Component
public class ScheduleJx3Tasks {
    @Autowired
    @Qualifier("jx3ServerOpenStatus")
    private Boolean jx3ServerOpenStatus;

    @Autowired
    private Jx3_service jx3Service;

    @Autowired
    private Gocq_service gocqService;

    @Autowired
    private GroupServiceService groupServiceService;

    @Scheduled(cron = "0/30 * * * * ?")
    public void getJx3ServerStatus() {
        Boolean status = jx3Service.get_server_open_status();
        System.out.println(status);
        if (status ^ jx3ServerOpenStatus) {
            List<GroupService> groupServiceList = groupServiceService.selectAllGroupServiceByServiceName(GroupServiceEnum.SERVER_OPEN_CONTINUS.getServiceName());
            if(jx3ServerOpenStatus){
                for (GroupService groupService : groupServiceList) {
                    gocqService.send_group_message(groupService.getGroup_id(), "破阵子，关服啦，侠士快他妈睡觉吧");
                }
                gocqService.send_group_message(1060106056, "破阵子，关服啦，侠士快他妈睡觉吧");
                gocqService.send_group_message(373957633, "破阵子，关服啦，侠士快他妈睡觉吧");
            } else {
                for (GroupService groupService : groupServiceList) {
                    gocqService.send_group_message(groupService.getGroup_id(), "破阵子，开服啦，侠士快他妈上线吧");
                }
                gocqService.send_group_message(1060106056, "破阵子，开服啦，侠士快他妈上线吧");
                gocqService.send_group_message(373957633, "破阵子，开服啦，侠士快他妈上线吧");
            }
            jx3ServerOpenStatus = status;
        }
    }
}
