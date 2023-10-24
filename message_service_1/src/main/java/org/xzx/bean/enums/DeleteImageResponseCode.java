package org.xzx.bean.enums;

import lombok.Getter;

@Getter
public enum DeleteImageResponseCode {
    IMAGE_NOT_EXIST(0, "图片不存在"),
    IMAGE_DELETE_SUCCESS(1, "图片删除成功"),
    IMAGE_DELETE_FAILED(2, "图片删除失败");

    private final int code;
    private final String message;

    DeleteImageResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
