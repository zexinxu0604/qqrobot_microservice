package org.xzx.bean.chatBean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AiModel {
    private String model_name;
    private String base_url;
    private String api_key;
}
