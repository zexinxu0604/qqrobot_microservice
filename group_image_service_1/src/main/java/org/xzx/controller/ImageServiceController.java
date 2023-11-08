package org.xzx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xzx.bean.GroupImage;
import org.xzx.bean.enums.ApiResultCode;
import org.xzx.bean.enums.CheckImageResponseCode;
import org.xzx.bean.enums.DeleteImageResponseCode;
import org.xzx.bean.enums.RestoreImageResponseCode;
import org.xzx.bean.response.*;
import org.xzx.service.GroupImageService;
import org.xzx.utils.String_Utils;
import org.xzx.utils.Url_utils;

@Controller
public class ImageServiceController {

    @Autowired
    private GroupImageService groupImageService;

    @Autowired
    private Url_utils url_utils;


    @RequestMapping("/getRandomImage")
    @ResponseBody
    public ImageResponse getRandomImage() {
        GroupImage groupImage = groupImageService.getRandomImage();
        String url = groupImage.getUrl();
        if (url_utils.checkUrl(url)) {
            return new ImageResponse(200, 0, url);
        } else {
            if (groupImageService.checkLocalPathExist(groupImage.getLocalurl())) {
                return new ImageResponse(200, 1, groupImage.getLocalurl());
            } else {
                groupImageService.deleteImage(url);
                return new ImageResponse(404, 0, null);
            }
        }
    }

    /**
     * 检查是否存在该图片，如果不存在则下载
     *
     * @param url
     * @return 0(已存在) 1(下载成功) 2(下载失败)
     */
    @GetMapping("/image/checkUrl")
    @ResponseBody
    public ApiResponse<CheckImageResponse> checkUrl(String url, long poster, long groupid) {
        String imageName = String_Utils.getImageName(url);
        if (groupImageService.isImageExist(imageName)) {
            return new ApiResponse<>(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMessage(), new CheckImageResponse(CheckImageResponseCode.IMAGE_IN_DATABASE));
        } else {
            return groupImageService.insertImage(url, poster, groupid) ? new ApiResponse<>(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMessage(), new CheckImageResponse(CheckImageResponseCode.IMAGE_DOWNLOAD_SUCCESS)) : new ApiResponse<>(ApiResultCode.FAILED.getCode(), ApiResultCode.FAILED.getMessage(), new CheckImageResponse(CheckImageResponseCode.IMAGE_DOWNLOAD_FAILED));
        }
    }

    @GetMapping("/image/insertImage")
    @ResponseBody
    public ApiResponse<CheckImageResponse> insertImage(String url, long poster, long groupid) {
        return groupImageService.insertImage(url, poster, groupid) ? new ApiResponse<>(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMessage(), new CheckImageResponse(CheckImageResponseCode.IMAGE_DOWNLOAD_SUCCESS)) : new ApiResponse<>(ApiResultCode.FAILED.getCode(), ApiResultCode.FAILED.getMessage(), new CheckImageResponse(CheckImageResponseCode.IMAGE_DOWNLOAD_FAILED));
    }

    @GetMapping("/image/deleteImage")
    @ResponseBody
    public ApiResponse<DeleteImageResponse> deleteImage(String url) {
        return groupImageService.deleteImage(url) ? new ApiResponse<>(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMessage(), new DeleteImageResponse(DeleteImageResponseCode.IMAGE_DELETE_SUCCESS)) : new ApiResponse<>(ApiResultCode.FAILED.getCode(), ApiResultCode.FAILED.getMessage(), new DeleteImageResponse(DeleteImageResponseCode.IMAGE_DELETE_FAILED));
    }

    @GetMapping("/image/restoreImage")
    @ResponseBody
    public ApiResponse<RestoreImageResponse> restoreImage(String url) {
        return groupImageService.restoreImage(url) ? new ApiResponse<>(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMessage(), new RestoreImageResponse(RestoreImageResponseCode.IMAGE_RESTORE_SUCCESS)) : new ApiResponse<>(ApiResultCode.FAILED.getCode(), ApiResultCode.FAILED.getMessage(), new RestoreImageResponse(RestoreImageResponseCode.IMAGE_RESTORE_FAILED));
    }
}
