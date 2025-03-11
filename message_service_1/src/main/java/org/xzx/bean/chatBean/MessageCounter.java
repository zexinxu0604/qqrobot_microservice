package org.xzx.bean.chatBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.xzx.bean.enums.GroupServiceEnum;
import org.xzx.configs.Constants;

import java.util.Map;

@Data
@AllArgsConstructor
public class MessageCounter {
    private long group_id;

    private Map<GroupServiceEnum, Integer> groupServiceEnumIntegerMap;

    private int messageCount;

    private int maxMessageCount;

    public void addMessageCount() {
        messageCount++;
    }

    public void addMessageCount(GroupServiceEnum groupServiceEnum) {
        groupServiceEnumIntegerMap.put(groupServiceEnum, groupServiceEnumIntegerMap.get(groupServiceEnum) + 1);
    }

    public void setMessageCount(GroupServiceEnum groupServiceEnum, int messageCount) {
        groupServiceEnumIntegerMap.put(groupServiceEnum, messageCount);
    }


    public MessageCounter(long group_id, int messageCount, int maxMessageCount) {
        this.group_id = group_id;
        this.messageCount = messageCount;
        this.maxMessageCount = maxMessageCount;
        this.groupServiceEnumIntegerMap = GroupServiceEnum.getGroupServiceCounterMap();
    }
}
