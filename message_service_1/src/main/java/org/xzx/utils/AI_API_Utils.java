package org.xzx.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.xzx.bean.chatBean.ChatAIRole;
import org.xzx.bean.enums.AiModels;
import org.xzx.configs.Constants;

import java.util.Objects;
import java.util.Queue;

@Component
@RefreshScope
public class AI_API_Utils {
    @Value("${chatgpt.chat.url}")
    private String chatUrl;

    @Value("${chatgpt.key}")
    private String chatGptKey;

    @Value("${deepseek.baseUrl}")
    private String deepSeekBaseUrl;

    @Value("${deepseek.apikey}")
    private String deepSeekApiKey;

    public String getChatUrl(String model_name) {
        if (AiModels.GPT_4o_MINI.getModel().equals(model_name) || AiModels.GPT_3_5_TURBO.getModel().equals(model_name)) {
            return chatUrl;
        }

        if (AiModels.DEEPSEEK_CHAT.getModel().equals(model_name) || AiModels.DEEPSEEK_REASONER.getModel().equals(model_name)) {
            return deepSeekBaseUrl;
        }

        return null;
    }

    public String getAPIKey(String model_name) {
        if (AiModels.GPT_4o_MINI.getModel().equals(model_name) || AiModels.GPT_3_5_TURBO.getModel().equals(model_name)) {
            return chatGptKey;
        }

        if (AiModels.DEEPSEEK_CHAT.getModel().equals(model_name) || AiModels.DEEPSEEK_REASONER.getModel().equals(model_name)) {
            return deepSeekApiKey;
        }

        return null;
    }

    public String getBeforeMessage(Queue<ChatAIRole> queue) {
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            ChatAIRole role = queue.poll();
            sb.append(role.getRole()).append(": ").append(role.getContent()).append("\n");
        }
        return sb.toString();
    }
}
