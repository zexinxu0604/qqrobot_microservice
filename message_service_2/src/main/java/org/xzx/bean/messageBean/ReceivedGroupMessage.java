package org.xzx.bean.messageBean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.xzx.bean.messageBean.messageSender.GroupMessageSender;

@Data
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceivedGroupMessage extends Message{

    private int group_id;

    private AnonymousMessage anonymous = null;

    private GroupMessageSender sender;
}
