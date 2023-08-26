package org.xzx.pojo.messageBean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.xzx.pojo.messageBean.messageSender.private_Message_Sender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
public class Received_Private_Message extends Message{

    private int target_id;

    private int temp_source = 0;

    private private_Message_Sender sender;
}
