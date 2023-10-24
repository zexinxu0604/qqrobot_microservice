package org.xzx.bean.response;

import lombok.Data;
import org.xzx.bean.enums.DeleteImageResponseCode;

@Data
public class DeleteImageResponse {
    private int code;
    private String message;

    public DeleteImageResponse(DeleteImageResponseCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
