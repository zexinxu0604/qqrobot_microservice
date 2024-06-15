package org.xzx.bean.qqGroupBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupInfo {
    private long group_id;

    private String group_name;

    private String group_memo;

    private int group_create_time;

    private int group_level;

    private int member_count;

    private int max_member_count;

}
