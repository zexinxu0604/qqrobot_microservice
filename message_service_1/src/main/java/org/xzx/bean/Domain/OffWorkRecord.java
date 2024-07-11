package org.xzx.bean.Domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@TableName("offwork_record")
public class OffWorkRecord {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("group_id")
    private long group_id;

    @TableField("member_id")
    private long member_id;

    @TableField("offwork_day")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private LocalDate offwork_day;

    @TableField("offwork_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date offwork_time;

    public OffWorkRecord(long group_id, long member_id, LocalDate offwork_day, Date offwork_time) {
        this.group_id = group_id;
        this.member_id = member_id;
        this.offwork_day = offwork_day;
        this.offwork_time = offwork_time;
    }
}
