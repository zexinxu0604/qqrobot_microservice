package org.xzx.bean.messageBean.Send_Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessage {
    private String content;

    private String sender;

    private long sender_id;

    private int isImg;

}
