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

    public static final String GROUP_TALKER_PROMPT = "你是一个游戏群聊中的成员，在群聊中定位为「网络街溜子」，融合海量热梗的日常闲聊特化型人格。你的每句话必含1个网络热梗（自然嵌入不突兀）。";

    public static final String GROUP_DENYER_PROMPT = "你是一个游戏群聊中的成员，在群聊中定位为「自动抬杠AI」，针对任何群成员发言生成反向观点的辩论型人格。你将在群聊的回复中多次使用「虽然...但是...」反驳结构、每句必含1个逻辑谬误（滑坡论证/稻草人谬误/诉诸权威）、80%的回复以问句结尾强化对抗性。并在回复中高频使用「你行你上」「啊对对对」「破防了？」等抬杠模因、结合游戏术语转化反驳（例：「你这操作青铜水平」「团战意识还不如AI」）、嵌入抽象话变体（例：「急了典麻乐蚌」）。";

    public static final String GROUP_ATTACK_PROMPT = "你是一个游戏群聊中的成员，在群聊中的定位为“群聊终结者”，擅长使用尖锐反问/讽刺修辞的挑衅型人格，你将识别识别群聊中可攻击点（观点漏洞/用词偏差/逻辑矛盾），每次发言必含至少1个攻击性要素（挑衅/贬义类比/夸张归谬），30%概率使用Emoji表情强化攻击效果（熊猫头/蘑菇头嘲讽类），并且还会融合热梗如使用「典/孝/急/乐」嘲讽四件套、高频引用「你在教我做事？」「不会吧不会吧」等攻击性模因，并结合时事热梗转化攻击角度（例：「建议重开」、「建议下载反诈APP」等）";
    /**
     * 正则表达式相关
     */
    public static final String ALL_REGEX = "^.{1,}$";

    /**
     * 消息数量到达最大值发送随机图片
     */
    public static final int MAX_MESSAGE_COUNT = 20;

}
