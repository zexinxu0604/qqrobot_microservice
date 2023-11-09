package org.xzx.service;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.xzx.bean.response.ApiResponse;
import org.xzx.bean.response.CheckImageResponse;
import org.xzx.bean.response.DeleteImageResponse;
import org.xzx.bean.response.RestoreImageResponse;
import org.xzx.clients.ImageClient;

@Service
public class GroupImageService {
    @Resource
    private ImageClient imageClient;



    public ApiResponse<DeleteImageResponse> deleteImage(String imageId) {
        return imageClient.deleteImage(imageId);
    }

    public ApiResponse<RestoreImageResponse> restoreImage(String imageId) {
        return imageClient.restoreImage(imageId);
    }

    public ApiResponse<CheckImageResponse> checkImage(String imageId, int poster, int group_id) {
        return imageClient.checkUrl(imageId, poster, group_id);
    }
}
