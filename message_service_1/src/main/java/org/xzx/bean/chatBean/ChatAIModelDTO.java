package org.xzx.bean.chatBean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.xzx.configs.Constants;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatAIModelDTO {
    String model;
    List<ChatAIRole> messages;

    public static ChatAIModelDTO create(String message) {
        ChatAIRole systemRole = new ChatAIRole("system", Constants.CHAT_SYSTEM_MESSAGE);
        ChatAIRole chatAIRole = new ChatAIRole("user", message);
        List<ChatAIRole> chatAIRoleList = List.of(systemRole, chatAIRole);
        return ChatAIModelDTO.builder()
                .model(Constants.GPT_4o_MINI)
                .messages(chatAIRoleList)
                .build();
    }
}
