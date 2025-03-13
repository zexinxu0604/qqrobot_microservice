package org.xzx.bean.Domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("group_service")
@AllArgsConstructor
public class GroupService {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("group_id")
    private Long group_id;

    @TableField("service_name")
    private String service_name;

    @TableField("status")
    private int status = 1;

    public GroupService(long group_id, String service_name) {
        this.group_id = group_id;
        this.service_name = service_name;
    }

    public GroupService(long group_id, String service_name, int status) {
        this.group_id = group_id;
        this.service_name = service_name;
        this.status = status;
    }
}
