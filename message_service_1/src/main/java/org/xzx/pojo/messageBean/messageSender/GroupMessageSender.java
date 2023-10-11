package org.xzx.pojo.messageBean.messageSender;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupMessageSender extends BasicSender {
    private String card;

    private String area;

    private String level;

    private String role;

    private String title;
}

