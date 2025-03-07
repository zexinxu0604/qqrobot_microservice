package org.xzx.bean.chatBean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupAIContext {
    private Long group_id;
    private AiModel aiModel;
    private List<ChatAIRole> context;
    private Date last_query_time;
}
