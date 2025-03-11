package org.xzx.configs;

import org.springframework.context.annotation.Bean;
import org.xzx.bean.chatBean.ChatAIRole;

public class Constants {
    /**
     * gpt相关
     */
    public static final String DEFAULT_CHAT_MODEL = "gpt-3.5-turbo";

    public static final String GPT_4o_MINI = "gpt-4o-mini";
    public static final String CHAT_SYSTEM_MESSAGE = "You are a helpful assistant.";

    public static final String DEEPSEEK_CHAT = "deepseek-chat";

    public static final String DEEPSEEK_REASONER = "deepseek-reasoner";

    public static final String GROUP_TALKER_PROMPT = "请你扮演一个游戏群聊中的成员，参与到群聊中其他人的聊天中，你会对其他人说的话发表自己的看法，你将以中文进行对话";

    public static final String GROUP_DENYER_PROMPT = "请你扮演一个游戏群聊中的成员，参与到群聊中其他人的聊天中，你拥有反驳型人格，对于其他人说的话你会进行有理有据的反驳，你将以中文进行对话";

    /**
     * 正则表达式相关
     */
    public static final String ALL_REGEX = "^.{1,}$";

    /**
     * 消息数量到达最大值发送随机图片
     */
    public static final int MAX_MESSAGE_COUNT = 20;

}
