package org.xzx.clients;

import org.springframework.stereotype.Component;
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
        return new ApiResponse<>(CheckImageResponseCode.IMAGE_DOWNLOAD_FAILED.getCode(), CheckImageResponseCode.IMAGE_DOWNLOAD_FAILED.getMessage(), new CheckImageResponse(CheckImageResponseCode.IMAGE_DOWNLOAD_FAILED));
    }

    @Override
    public ApiResponse<CheckImageResponse> insertImage(String url, long poster, long groupid) {
        return new ApiResponse<>(CheckImageResponseCode.IMAGE_DOWNLOAD_FAILED.getCode(), CheckImageResponseCode.IMAGE_DOWNLOAD_FAILED.getMessage(), new CheckImageResponse(CheckImageResponseCode.IMAGE_DOWNLOAD_FAILED));
    }

    @Override
    public ApiResponse<DeleteImageResponse> deleteImage(String url) {
        return new ApiResponse<>(DeleteImageResponseCode.IMAGE_DELETE_FAILED.getCode(), DeleteImageResponseCode.IMAGE_DELETE_FAILED.getMessage(), new DeleteImageResponse(DeleteImageResponseCode.IMAGE_DELETE_FAILED));
    }

    @Override
    public ApiResponse<RestoreImageResponse> restoreImage(String url) {
        return new ApiResponse<>(RestoreImageResponseCode.IMAGE_RESTORE_FAILED.getCode(), RestoreImageResponseCode.IMAGE_RESTORE_FAILED.getMessage(), new RestoreImageResponse(RestoreImageResponseCode.IMAGE_RESTORE_FAILED));
    }

}
