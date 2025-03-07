package org.xzx.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xzx.bean.chatBean.AiModel;
import org.xzx.bean.chatBean.GroupAIContext;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class AiModelConfig {

    @Value("${chatgpt.key}")
    private String chatgptKey;

    @Value("${chatgpt.chat.url}")
    private String chatgptUrl;

    @Value("${deepseek.baseUrl}")
    private String deepseekBaseUrl;

    @Value("${deepseek.apikey}")
    private String deepseekApiKey;

    @Bean(name = "gpt-35")
    public AiModel getChatGptModel() {
        return new AiModel(Constants.DEFAULT_CHAT_MODEL, chatgptUrl, chatgptKey);
    }

    @Bean(name = "gpt-4o")
    public AiModel getChatGptModel2() {
        return new AiModel(Constants.GPT_4o_MINI, chatgptUrl, chatgptKey);
    }

    @Bean(name = "deepseek-chat")
    public AiModel getDeepSeekChatModel() {
        return new AiModel(Constants.DEEPSEEK_CHAT, deepseekBaseUrl, deepseekApiKey);
    }

    @Bean(name = "deepseek-reasoner")
    public AiModel getDeepSeekReasonerModel() {
        return new AiModel(Constants.DEEPSEEK_REASONER, deepseekBaseUrl, deepseekApiKey);
    }

    @Bean(name = "group-chat-context")
    public Map<Long, GroupAIContext> getGroupAIChatContext() {
        return new ConcurrentHashMap<>();
    }

}
