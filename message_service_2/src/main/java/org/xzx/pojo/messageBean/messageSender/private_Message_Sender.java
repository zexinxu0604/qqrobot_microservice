package org.xzx.pojo.messageBean.messageSender;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
public class private_Message_Sender extends basic_Sender{

    private int group_id = 0;

}
