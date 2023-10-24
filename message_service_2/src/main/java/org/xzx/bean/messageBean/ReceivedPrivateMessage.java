package org.xzx.bean.messageBean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.xzx.bean.messageBean.messageSender.PrivateMessageSender;

@Data
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceivedPrivateMessage extends Message{

    private int target_id;

    private int temp_source = 0;

    private PrivateMessageSender sender;
}
