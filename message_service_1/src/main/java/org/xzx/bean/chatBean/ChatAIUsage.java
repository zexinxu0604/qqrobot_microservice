package org.xzx.bean.chatBean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatAIUsage {
    int prompt_tokens;
    int completion_tokens;
    int total_tokens;
}
