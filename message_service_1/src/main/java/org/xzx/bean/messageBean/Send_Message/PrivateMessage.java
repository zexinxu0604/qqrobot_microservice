package org.xzx.bean.messageBean.Send_Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivateMessage {
    private int user_id;

    private String message;

    private boolean auto_escape = false;

    public PrivateMessage(int userId, String message) {
        this.user_id = userId;
        this.message = message;
    }
}
