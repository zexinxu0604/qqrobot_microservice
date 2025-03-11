package org.xzx.bean.chatBean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.xzx.bean.enums.AiCharacters;
import org.xzx.bean.enums.AiModels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class GroupAIContext {
    private Long group_id;
    private String aiModel;
    private List<ChatAIRole> context;
    private AiCharacters aiCharacters;
    private Date last_query_time;

    public GroupAIContext(Long group_id, String aiModel) {
        this.group_id = group_id;
        this.aiModel = aiModel;
        ChatAIRole role = new ChatAIRole();
        role.setRole("system");
        role.setContent("You are a helpful assistant.");
        List<ChatAIRole> context = new ArrayList<>();
        context.add(role);
        this.context = context;
        this.last_query_time = new Date();
        this.aiCharacters = AiCharacters.GroupTalker;
    }

    public GroupAIContext(Long group_id, String aiModel, AiCharacters aiCharacters) {
        this.group_id = group_id;
        this.aiModel = aiModel;
        ChatAIRole role = new ChatAIRole();
        role.setRole("system");
        role.setContent("You are a helpful assistant.");
        List<ChatAIRole> context = new ArrayList<>();
        context.add(role);
        this.context = context;
        this.last_query_time = new Date();
        this.aiCharacters = aiCharacters;
    }
}
