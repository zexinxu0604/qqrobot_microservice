package org.xzx.bean.messageUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageCounter {
    private int group_id;

    private int messageCount;

    private int maxMessageCount;

    public void addMessageCount() {
        messageCount++;
    }
}
