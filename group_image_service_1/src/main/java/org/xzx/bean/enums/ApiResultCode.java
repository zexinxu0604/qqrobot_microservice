package org.xzx.bean.enums;

import lombok.Getter;

@Getter
public enum ApiResultCode {
    SUCCESS(200, "成功"),
    FAILED(500, "失败"),
    VALIDATE_FAILED(404, "参数检验失败");

    private final int code;
    private final String message;

    ApiResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
