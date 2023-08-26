package org.xzx.pojo.messageBean.Send_Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group_Message {
    private int group_id;

    private String message;

    private boolean auto_escape = false;

    public Group_Message(int groupId, String message) {
        this.group_id = groupId;
        this.message = message;
    }
}
