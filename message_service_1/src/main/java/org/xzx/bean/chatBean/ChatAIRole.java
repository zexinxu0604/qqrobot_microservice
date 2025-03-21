package org.xzx.bean.chatBean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.xzx.configs.Constants;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatAIRole {
    String role;
    String content;

    public static ChatAIRole offlimitRole() {
        return new ChatAIRole("system", Constants.OFF_LIMIT_PROMPT);
    }
}
