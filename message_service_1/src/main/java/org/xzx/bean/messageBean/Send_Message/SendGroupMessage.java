package org.xzx.bean.messageBean.Send_Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendGroupMessage {
    private long group_id;

    private String message;

    private boolean auto_escape = false;

    public SendGroupMessage(long groupId, String message) {
        this.group_id = groupId;
        this.message = message;
    }
}
