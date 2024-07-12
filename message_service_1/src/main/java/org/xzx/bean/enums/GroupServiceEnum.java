package org.xzx.bean.enums;

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

    /**
     * 其他服务
     */
    OPEN_SERVICE("open_service", "开启服务", "开启 服务名"),
    CLOSE_SERVICE("close_service", "关闭服务", "关闭 服务名"),
    GPT_CHAT("gpt_chat", "GPT聊天", "@机器人 聊天内容"),

    RANDOM_PICTURE("random_picture", "机器人表情包水群", "无触发语句，自动触发"),
    AT_RANDOM_PICTURE("at_random_picture", "表情包", "@机器人即可"),

    STEAL_IMAGE("steal_image", "偷图", "暂时关闭"),

    OFF_WORK_RECORD("off_work_record", "下班打卡", "发送下班或者以下班开头不超过三个字的消息即可打卡");

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
}
