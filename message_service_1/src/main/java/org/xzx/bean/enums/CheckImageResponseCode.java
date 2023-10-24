package org.xzx.bean.enums;

import lombok.Getter;

@Getter
public enum CheckImageResponseCode {
    IMAGE_IN_DATABASE(0, "图片已存在"),
    IMAGE_DOWNLOAD_SUCCESS(1, "图片下载成功"),
    IMAGE_DOWNLOAD_FAILED(2, "图片下载失败");

    private final int code;
    private final String message;

    CheckImageResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
