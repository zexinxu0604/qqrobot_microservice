package org.xzx.service;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.xzx.bean.chatBean.ChatAIRole;
import org.xzx.bean.chatBean.GroupAIContext;
import org.xzx.bean.enums.AiModels;
import org.xzx.configs.Constants;
import org.xzx.utils.AI_API_Utils;

import java.util.*;

@Service
@RefreshScope
@Slf4j
public class ChatAIService {

    @Autowired
    @Qualifier("group-chat-context")
    private Map<Long, GroupAIContext> groupAIContextMap;

    @Autowired
    private AI_API_Utils aiApiUtils;

    public String getChatAIResponse(long group_id, String message) {
        GroupAIContext groupAIContext = groupAIContextMap.getOrDefault(group_id, null);
        if (Objects.isNull(groupAIContext)) {
            groupAIContextMap.put(group_id, new GroupAIContext(group_id, AiModels.DEEPSEEK_CHAT.getModel()));
        }

        groupAIContext = groupAIContextMap.get(group_id);
        Date now = new Date();
        if (now.getTime() - groupAIContext.getLast_query_time().getTime() > 1000 * 60 * 5) {
            groupAIContextMap.put(group_id, new GroupAIContext(group_id, groupAIContext.getAiModel(), groupAIContext.getAiCharacters()));
        }
        String model_name = groupAIContext.getAiModel();
        String baseUrl = aiApiUtils.getChatUrl(model_name);
        String apiKey = aiApiUtils.getAPIKey(model_name);

        try{
            OpenAIClient openAIClient = OpenAIOkHttpClient.builder().baseUrl(baseUrl).apiKey(apiKey).build();
            List<ChatAIRole> history_messages = groupAIContextMap.get(group_id).getContext();
            ChatCompletionCreateParams chatCompletionCreateParams = createChatCompletionParams(model_name, history_messages, message);
            ChatCompletion chatCompletion = openAIClient.chat().completions().create(chatCompletionCreateParams);

            List<ChatCompletion.Choice> choices = chatCompletion.choices();
            String response = choices.get(0).message().content().get();

            ChatAIRole new_role = new ChatAIRole("assistant", response);
            history_messages.add(new_role);
            groupAIContextMap.get(group_id).setContext(history_messages);
            String answer = "";
            if (model_name.equals(Constants.DEEPSEEK_REASONER)) {
                String reasoning_content = choices.get(0).message()._additionalProperties().get("reasoning_content").toString();
                answer = "深度思考:\n**" + reasoning_content + "**\n\n";
            }

            answer += response;
            return answer;
        } catch (Exception e) {
            e.printStackTrace();
            return "AI服务出现错误";
        }
    }

    public String getRandomAIReply(long group_id, String message) {
        try{

            GroupAIContext groupAIContext = groupAIContextMap.getOrDefault(group_id, null);
            if (Objects.isNull(groupAIContext)) {
                groupAIContextMap.put(group_id, new GroupAIContext(group_id, AiModels.DEEPSEEK_CHAT.getModel()));
            }

            groupAIContext = groupAIContextMap.get(group_id);

            String model_name = AiModels.DEEPSEEK_CHAT.getModel();
            String baseUrl = aiApiUtils.getChatUrl(model_name);
            String apiKey = aiApiUtils.getAPIKey(model_name);

            List<ChatAIRole> history_messages = new ArrayList<>();
            history_messages.add(new ChatAIRole("system", groupAIContext.getAiCharacters().getCharacter()));

            OpenAIClient openAIClient = OpenAIOkHttpClient.builder().baseUrl(baseUrl).apiKey(apiKey).build();
            ChatCompletionCreateParams chatCompletionCreateParams = createChatCompletionParams(model_name, history_messages, message);
            ChatCompletion chatCompletion = openAIClient.chat().completions().create(chatCompletionCreateParams);

            List<ChatCompletion.Choice> choices = chatCompletion.choices();
            String response = choices.get(0).message().content().get();
            String answer = "";
            answer += response;
            return answer;
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    private ChatCompletionCreateParams createChatCompletionParams(String model_name, List<ChatAIRole> history_messages, String message) {
        List<ChatCompletionMessageParam> messages = new ArrayList<>();
        ChatAIRole new_role = new ChatAIRole("user", message);
        history_messages.add(new_role);
        for (ChatAIRole chatAIRole : history_messages) {
            if (chatAIRole.getRole().equals("user")) {
                ChatCompletionUserMessageParam chatCompletionUserMessageParam = ChatCompletionUserMessageParam.builder().content(chatAIRole.getContent()).build();
                ChatCompletionMessageParam chatCompletionMessageParam = ChatCompletionMessageParam.ofUser(chatCompletionUserMessageParam);
                messages.add(chatCompletionMessageParam);
            } else if (chatAIRole.getRole().equals("system")) {
                ChatCompletionSystemMessageParam chatCompletionSystemMessageParam = ChatCompletionSystemMessageParam.builder().content(chatAIRole.getContent()).build();
                ChatCompletionMessageParam chatCompletionMessageParam = ChatCompletionMessageParam.ofSystem(chatCompletionSystemMessageParam);
                messages.add(chatCompletionMessageParam);
            } else if (chatAIRole.getRole().equals("assistant")) {
                ChatCompletionAssistantMessageParam chatCompletionAssistantMessageParam = ChatCompletionAssistantMessageParam.builder().content(chatAIRole.getContent()).build();
                ChatCompletionMessageParam chatCompletionMessageParam = ChatCompletionMessageParam.ofAssistant(chatCompletionAssistantMessageParam);
                messages.add(chatCompletionMessageParam);
            }
        }
        return ChatCompletionCreateParams.builder().messages(messages).model(model_name).build();
    }
}
