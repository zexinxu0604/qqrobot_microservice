package org.xzx.pojo.messageBean.Send_Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendGroupMessage {
    private int group_id;

    private String message;

    private boolean auto_escape = false;

    public SendGroupMessage(int groupId, String message) {
        this.group_id = groupId;
        this.message = message;
    }
}
