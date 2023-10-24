package org.xzx.bean.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {
    private int status;

    private int type;

    private String url;
}
