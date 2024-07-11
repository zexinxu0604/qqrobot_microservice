package org.xzx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xzx.bean.Domain.OffWorkRecord;
import org.xzx.dao.OffWorkRecordMapper;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class OffWorkRecordService {

    @Autowired
    private OffWorkRecordMapper offWorkRecordMapper;

    public boolean insertOffWorkRecord(OffWorkRecord offWorkRecord) {
        return offWorkRecordMapper.insert(new OffWorkRecord(offWorkRecord.getGroup_id(), offWorkRecord.getMember_id(), offWorkRecord.getOffwork_day(), offWorkRecord.getOffwork_time())) == 1;
    }

    public OffWorkRecord selectOffWorkRecordByGroupIdAndMemberIdAndOffworkDay(long group_id, long member_id, LocalDate offwork_day) {
        QueryWrapper<OffWorkRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id", group_id).eq("member_id", member_id).eq("offwork_day", offwork_day);
        return offWorkRecordMapper.selectOne(queryWrapper);
    }

    public boolean updateOffWorkRecordByGroupIdAndMemberIdAndOffworkDay(long group_id, long member_id, LocalDate offwork_day, Date offwork_time) {
        OffWorkRecord offWorkRecord = new OffWorkRecord(group_id, member_id, offwork_day, offwork_time);
        QueryWrapper<OffWorkRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id", group_id).eq("member_id", member_id).eq("offwork_day", offwork_day);
        return offWorkRecordMapper.update(offWorkRecord, queryWrapper) == 1;
    }

    public List<OffWorkRecord> selectAllOffWorkRecordByGroupIdAndToday(long group_id, LocalDate offwork_day) {
        QueryWrapper<OffWorkRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id", group_id).eq("offwork_day", offwork_day);
        return offWorkRecordMapper.selectList(queryWrapper);
    }

    public List<OffWorkRecord> selectAllOffWorkRecordByGroupIdAndTodayAndTimeAfterYesterday(long group_id, LocalDate offwork_day, Date offwork_time) {
        QueryWrapper<OffWorkRecord> queryWrapper = new QueryWrapper<>();
        Date yesterday = new Date(offwork_time.getTime() - 24 * 60 * 60 * 1000);
        queryWrapper.eq("group_id", group_id).eq("offwork_day", offwork_day).ge("offwork_time", yesterday);
        return offWorkRecordMapper.selectList(queryWrapper);
    }

}
