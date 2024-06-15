package org.xzx.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.xzx.bean.chatBean.ChatAIModelDTO;
import org.xzx.bean.chatBean.ChatAIRespDTO;

@Service
@RefreshScope
public class ChatAIService {
    @Autowired
    private SpringRestService springRestService;

    @Value("${chatgpt.key}")
    private String chatgptKey;

    @Value("${chatgpt.chat.url}")
    private String chatgptUrl;

    private HttpHeaders headers_with_chatgpt_key;
    @PostConstruct
    public void init() {
        headers_with_chatgpt_key = new HttpHeaders();
        headers_with_chatgpt_key.set("Authorization", "Bearer " + chatgptKey);
        headers_with_chatgpt_key.set("Content-Type", "application/json");
    }
    public String getChatAIResponse(String message) {
        ChatAIModelDTO chatAIModelDTO = ChatAIModelDTO.create(message);
        ChatAIRespDTO chatAIRespDTO = springRestService.postWithObject(chatgptUrl, headers_with_chatgpt_key, chatAIModelDTO, ChatAIRespDTO.class);
        return chatAIRespDTO.getChoices().get(0).getMessage().getContent();
    }
}
