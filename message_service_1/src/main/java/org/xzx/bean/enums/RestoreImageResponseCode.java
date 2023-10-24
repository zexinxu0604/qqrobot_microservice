package org.xzx.bean.enums;

import lombok.Getter;

@Getter
public enum RestoreImageResponseCode {
    IMAGE_NOT_EXIST(0, "图片不存在"),
    IMAGE_RESTORE_SUCCESS(1, "图片恢复成功"),
    IMAGE_RESTORE_FAILED(2, "图片恢复失败");

    private final int code;
    private final String message;

    RestoreImageResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
