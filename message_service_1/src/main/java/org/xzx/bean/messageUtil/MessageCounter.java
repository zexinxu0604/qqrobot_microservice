package org.xzx.bean.messageUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.xzx.configs.Constants;

@Data
@AllArgsConstructor
public class MessageCounter {
    private long group_id;

    private int messageCount;

    private int maxMessageCount;

    public void addMessageCount() {
        messageCount++;
    }

    public MessageCounter(long group_id, int messageCount) {
        this.group_id = group_id;
        this.messageCount = messageCount;
        this.maxMessageCount = Constants.MAX_MESSAGE_COUNT;
    }
}
