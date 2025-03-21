package org.xzx.bean.chatBean;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.xzx.bean.Domain.AICharacter;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
public class GroupAIContext {
    private Long group_id;
    private String aiModel;
    private List<ChatAIRole> context;
    private AICharacter aiCharacters;
    private Date last_query_time;

    public GroupAIContext(Long group_id, String aiModel) {
        this.group_id = group_id;
        this.aiModel = aiModel;
        ChatAIRole role = new ChatAIRole();
        role.setRole("system");
        role.setContent("You are a helpful assistant.");
        List<ChatAIRole> context = new LinkedList<>();
        context.add(role);
        this.context = context;
        this.last_query_time = new Date();
        this.aiCharacters = AICharacter.createDefaultAICharacter();
    }

    public GroupAIContext(Long group_id, String aiModel, AICharacter aiCharacters) {
        this.group_id = group_id;
        this.aiModel = aiModel;
        ChatAIRole role = new ChatAIRole();
        role.setRole("system");
        role.setContent(aiCharacters.getCharacterPrompt());
        List<ChatAIRole> context = new LinkedList<>();
        context.add(role);
        this.context = context;
        this.last_query_time = new Date();
        this.aiCharacters = aiCharacters;
    }
}
