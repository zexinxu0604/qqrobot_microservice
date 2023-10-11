package org.xzx.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ImageResponse
 * 用于接收请求图片
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {
    private int status;

    private int type;

    private String url;
}
