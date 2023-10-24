package org.xzx.bean.messageBean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor


@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    private String post_type;

    private String message_type;

    private String sub_type;

    private int message_id;

    private int user_id;

    private String message;

    private String raw_message;

    private int font = 0;

}
