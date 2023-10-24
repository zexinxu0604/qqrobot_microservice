package org.xzx.bean.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.xzx.bean.enums.CheckImageResponseCode;

@Data
@AllArgsConstructor
public class CheckImageResponse {
    private int code;
    private String message;

    public CheckImageResponse(CheckImageResponseCode code){
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
