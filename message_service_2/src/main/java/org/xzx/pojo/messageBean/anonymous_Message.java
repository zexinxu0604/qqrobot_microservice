package org.xzx.pojo.messageBean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
id      匿名用户 ID
name	匿名用户名称
flag    匿名用户 flag, 在调用禁言 API 时需要传入
*/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
public class anonymous_Message {
    private int id;

    private String name;

    private String flag;
}
