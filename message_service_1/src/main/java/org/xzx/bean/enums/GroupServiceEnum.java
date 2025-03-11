package org.xzx.bean.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public enum GroupServiceEnum {
    /**
     * 剑网三服务名称
     */
    SERVER_OPEN("server_open", "开服", "开服或开服 服务器名"),
    SERVER_OPEN_CONTINUS("server_open_continus", "开服监控", "无触发语句，自动监控开服"),
    JX3_DAILY("jx3_daily", "剑网三日常", "日常"),
    JX3_BaiZhan("jx3_baizhan", "剑网三百战", "百战"),
    JX3_FIREWORK("jx3_firework", "剑网三烟花", "烟花 角色名"),
    JX3_BOSS_TREASURE("jx3_boss_treasure", "剑网三掉落查询", "掉落 掉落名"),
    JX3_BLACK_TRADE("jx3_black_trade", "剑网三黑市", "物价 物品名"),
    JX3_GOLD_PRICE("jx3_gold_price", "剑网三金价", "金价或金价 服务器名"),
    JX3_LUCK("jx3_luck", "剑网三奇遇", "查询 角色名"),
    JX3_ATTRIBUTE("jx3_attribute", "角色属性", "属性 角色名或属性 服务器名 角色名"),
    JX3_TEAM_RECRUIT("jx3_team_recruit", "剑网三招募", "招募 招募内容"),

    TIEBA_ITEM("tieba_item", "贴吧物品查询", "贴吧 物品名或贴吧 服务器名 物品名"),

    TIEBA_CHIGUA("tieba_bagua", "贴吧八卦", "吃瓜 类型 （可选范围：818 616 鬼网三 鬼网3 树洞 记录 教程 街拍 故事 避雷 吐槽 提问）"),

    AVATAR_CHIGUA("avatar_bagua", "阿瓦达啃大瓜", "阿瓦达啃大瓜 贴子链接"),

    JX3_ROLE_CARD("jx3_role_card", "剑网三名片", "名片 角色名或 名片 服务器名 角色名"),

    RAMDOM_JX3_ROLE_CARD("random_jx3_role_card", "随机剑网三名片", "随机名片 或 随机名片 服务器名"),

    /**
     * 其他服务
     */
    OPEN_SERVICE("open_service", "开启服务", "开启 服务名"),
    CLOSE_SERVICE("close_service", "关闭服务", "关闭 服务名"),
    GPT_CHAT("gpt_chat", "GPT聊天，目前支持多种模型", "@机器人 聊天内容"),

    RANDOM_PICTURE("random_picture", "机器人表情包水群", "无触发语句，自动触发"),
    AT_RANDOM_PICTURE("at_random_picture", "表情包", "@机器人即可"),

    STEAL_IMAGE("steal_image", "偷图", "暂时关闭"),

    OFF_WORK_RECORD("off_work_record", "下班打卡", "发送下班或者以下班开头不超过三个字的消息即可打卡"),

    LIST_ALL_AI_MODEL("list_all_ai_model", "列出所有可用AI模型", "模型列表"),

    CHANGE_AI_MODEL("change_ai_model", "切换AI模型", "切换模型 模型名"),

    AI_RANDOM_CHAT("ai_random_chat", "AI随机聊天", "无触发语句，自动触发"),

    CHANGE_AI_CHARACTER("change_ai_character", "切换AI角色", "切换角色 角色名");

    private final String serviceName;

    private final String serviceDesc;

    private final String serviceTrigger;

    GroupServiceEnum(String serviceName, String serviceDesc, String serviceTrigger) {
        this.serviceName = serviceName;
        this.serviceDesc = serviceDesc;
        this.serviceTrigger = serviceTrigger;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public String getServiceTrigger() {
        return serviceTrigger;
    }

    public static GroupServiceEnum getGroupServiceEnumByServiceName(String serviceName) {
        for (GroupServiceEnum groupServiceEnum : GroupServiceEnum.values()) {
            if (groupServiceEnum.getServiceName().equals(serviceName)) {
                return groupServiceEnum;
            }
        }
        return null;
    }

    public static GroupServiceEnum getGroupServiceEnumByServiceDesc(String serviceDesc) {
        for (GroupServiceEnum groupServiceEnum : GroupServiceEnum.values()) {
            if (groupServiceEnum.getServiceDesc().equals(serviceDesc)) {
                return groupServiceEnum;
            }
        }
        return null;
    }

    public static Map<GroupServiceEnum, String> getGroupServiceEnumMap() {
        Map<GroupServiceEnum, String> groupServiceEnumMap = new HashMap<>();
        for (GroupServiceEnum groupServiceEnum : GroupServiceEnum.values()) {
            groupServiceEnumMap.put(groupServiceEnum, groupServiceEnum.getServiceName());
        }
        return groupServiceEnumMap;
    }

    public static Map<GroupServiceEnum, ReentrantLock> getAllServiceLockMap() {
        Map<GroupServiceEnum, ReentrantLock> lockMap = new HashMap<>();
        for (GroupServiceEnum groupServiceEnum : GroupServiceEnum.values()) {
            lockMap.put(groupServiceEnum, new ReentrantLock());
        }
        return lockMap;
    }

    public static Map<GroupServiceEnum, Integer> getGroupServiceCounterMap() {
        Map<GroupServiceEnum, Integer> counterMap = new HashMap<>();
        for (GroupServiceEnum groupServiceEnum : GroupServiceEnum.values()) {
            counterMap.put(groupServiceEnum, 0);
        }
        return counterMap;
    }

}
