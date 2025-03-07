package org.xzx.service;
import com.openai.client.OpenAIClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.xzx.bean.chatBean.ChatAIModelDTO;
import org.xzx.bean.chatBean.ChatAIRespDTO;
import org.xzx.bean.chatBean.GroupAIContext;
import java.util.Map;

@Service
@RefreshScope
public class ChatAIService {

    @Autowired
    @Qualifier("group-chat-context")
    private Map<Long, GroupAIContext> groupAIContextMap;
    public String getChatAIResponse(long group_id, String message) {

    }
}
