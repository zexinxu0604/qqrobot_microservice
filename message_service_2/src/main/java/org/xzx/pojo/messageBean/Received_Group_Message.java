package org.xzx.pojo.messageBean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.xzx.pojo.messageBean.messageSender.group_Message_Sender;

@Data
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
public class Received_Group_Message extends Message{

    private int group_id;

    private anonymous_Message anonymous = null;

    private group_Message_Sender sender;
}
