package org.xzx.scheduleTasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xzx.bean.Domain.OffWorkRecord;
import org.xzx.bean.enums.GroupServiceEnum;
import org.xzx.service.Gocq_service;
import org.xzx.service.GroupServiceService;
import org.xzx.service.OffWorkRecordService;
import org.xzx.utils.CQ_Generator_Utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Component
public class ScheduleGroupServiceTask {
    @Autowired
    private GroupServiceService groupServiceService;

    @Autowired
    private OffWorkRecordService offWorkRecordService;

    @Autowired
    private Gocq_service gocqService;

    @Scheduled(cron = "0 0 7 * * ?")
    public void updateGroupService() {
        List<Long> groupList = groupServiceService.selectAllGroupByServiceName(GroupServiceEnum.OFF_WORK_RECORD.getServiceName());
        for (Long group_id : groupList) {
            LocalDate today = LocalDate.now();
            LocalDate yesterday = today.minusDays(1);
            LocalTime localTime = LocalTime.now();
            OffWorkRecord latestOffWorkRecord = null;
            if (localTime.getHour() < 8) {
                List<OffWorkRecord> offWorkRecordsToday = offWorkRecordService.selectAllOffWorkRecordByGroupIdAndToday(group_id, today);
                List<OffWorkRecord> offWorkRecordsYesterday = offWorkRecordService.selectAllOffWorkRecordByGroupIdAndTodayAndTimeAfterYesterday(group_id, yesterday, new Date());
                for (OffWorkRecord offWorkRecord : offWorkRecordsToday) {
                    if (latestOffWorkRecord == null || latestOffWorkRecord.getOffwork_time().before(offWorkRecord.getOffwork_time())) {
                        latestOffWorkRecord = offWorkRecord;
                    }
                }
                for (OffWorkRecord offWorkRecord : offWorkRecordsYesterday) {
                    if (latestOffWorkRecord == null || latestOffWorkRecord.getOffwork_time().before(offWorkRecord.getOffwork_time())) {
                        latestOffWorkRecord = offWorkRecord;
                    }
                }

                if (latestOffWorkRecord != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    gocqService.send_group_message(group_id, String.format("昨天的加班王是！%s, ta在%s下班！", CQ_Generator_Utils.getAtString(latestOffWorkRecord.getMember_id()), sdf.format(latestOffWorkRecord.getOffwork_time())));
                } else {
                    gocqService.send_group_message(group_id, "昨天没有下班记录！");
                }
            }

        }
    }

}
