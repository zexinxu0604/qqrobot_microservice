package org.xzx.clients;

import org.springframework.stereotype.Component;
import org.xzx.bean.enums.ApiResultCode;
import org.xzx.bean.enums.CheckImageResponseCode;
import org.xzx.bean.enums.DeleteImageResponseCode;
import org.xzx.bean.enums.RestoreImageResponseCode;
import org.xzx.bean.response.*;

@Component
public class ImageClientFallBack implements ImageClient{

    @Override
    public ImageResponse getRandomImage() {
        return null;
    }

    @Override
    public ApiResponse<CheckImageResponse> checkUrl(String url, long poster, long groupid) {
        return new ApiResponse<>(ApiResultCode.FAILED.getCode(), ApiResultCode.FAILED.getMessage(), null);
    }

    @Override
    public ApiResponse<CheckImageResponse> insertImage(String url, long poster, long groupid) {
        return new ApiResponse<>(ApiResultCode.FAILED.getCode(), ApiResultCode.FAILED.getMessage(), null);
    }

    @Override
    public ApiResponse<DeleteImageResponse> deleteImage(String url) {
        return new ApiResponse<>(ApiResultCode.FAILED.getCode(), ApiResultCode.FAILED.getMessage(), null);
    }

    @Override
    public ApiResponse<RestoreImageResponse> restoreImage(String url) {
        return new ApiResponse<>(ApiResultCode.FAILED.getCode(), ApiResultCode.FAILED.getMessage(), null);
    }

}
