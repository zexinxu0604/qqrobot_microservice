package org.xzx.bean.qqGroupBean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class GroupService {
    private long id;
    private long group_id;
    private String service_name;
    private int status = 1;

    public GroupService(long group_id, String service_name) {
        this.group_id = group_id;
        this.service_name = service_name;
    }
}
