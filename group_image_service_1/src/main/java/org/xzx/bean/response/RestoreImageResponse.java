package org.xzx.bean.response;

import lombok.Data;
import org.xzx.bean.enums.RestoreImageResponseCode;

@Data
public class RestoreImageResponse {
    private int code;
    private String message;

    public RestoreImageResponse(RestoreImageResponseCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
