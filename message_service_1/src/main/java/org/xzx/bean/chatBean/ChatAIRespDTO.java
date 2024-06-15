package org.xzx.bean.chatBean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatAIRespDTO {
    String id;
    String object;
    long created;
    List<ChatAIRespChoices> choices;
    ChatAIUsage usage;
}
