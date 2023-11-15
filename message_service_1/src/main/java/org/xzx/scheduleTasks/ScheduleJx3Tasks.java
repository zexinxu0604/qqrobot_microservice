package org.xzx.scheduleTasks;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xzx.service.Gocq_service;
import org.xzx.service.Jx3_service;

@Component
public class ScheduleJx3Tasks {
    @Autowired
    @Qualifier("jx3ServerOpenStatus")
    private Boolean jx3ServerOpenStatus;

    @Autowired
    private Jx3_service jx3Service;

    @Autowired
    private Gocq_service gocqService;

    @Scheduled(cron = "0/30 * * * * ?")
    public void getJx3ServerStatus() {
        Boolean status = jx3Service.get_server_open_status();
        System.out.println(status);
        if (status ^ jx3ServerOpenStatus) {
            if(jx3ServerOpenStatus){
                gocqService.send_group_message(1060106056, "破阵子，关服啦，侠士快他妈睡觉吧");
                gocqService.send_group_message(373957633, "破阵子，关服啦，侠士快他妈睡觉吧");
            } else {
                gocqService.send_group_message(1060106056, "破阵子，开服啦，侠士快他妈上线吧");
                gocqService.send_group_message(373957633, "破阵子，开服啦，侠士快他妈上线吧");
            }
            jx3ServerOpenStatus = status;
        }
    }
}
