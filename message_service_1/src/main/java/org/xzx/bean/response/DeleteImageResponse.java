package org.xzx.bean.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.xzx.bean.enums.DeleteImageResponseCode;

@Data
@AllArgsConstructor
public class DeleteImageResponse {
    private int code;
    private String message;

    public DeleteImageResponse(DeleteImageResponseCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
