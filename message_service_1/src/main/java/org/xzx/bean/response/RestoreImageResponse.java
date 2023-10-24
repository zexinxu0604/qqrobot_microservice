package org.xzx.bean.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.xzx.bean.enums.RestoreImageResponseCode;

@Data
@AllArgsConstructor
public class RestoreImageResponse {
    private int code;
    private String message;

    public RestoreImageResponse(RestoreImageResponseCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
