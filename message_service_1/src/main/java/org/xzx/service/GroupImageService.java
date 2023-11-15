package org.xzx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xzx.bean.ImageBean.GroupImage;
import org.xzx.bean.enums.ApiResultCode;
import org.xzx.bean.enums.CheckImageResponseCode;
import org.xzx.bean.enums.DeleteImageResponseCode;
import org.xzx.bean.enums.RestoreImageResponseCode;
import org.xzx.bean.response.*;
import org.xzx.dao.GroupImageDao;
import org.xzx.utils.String_Utils;
import org.xzx.utils.Url_utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Log4j2
public class GroupImageService {

    @Autowired
    private GroupImageDao groupImageDao;

    @Autowired
    private Url_utils url_utils;

    @Value("${qq.group.imagePath}")
    private String imagepath;


    public ApiResponse<DeleteImageResponse> deleteImage(String url) {
        return deleteImageFromBase(url) ? new ApiResponse<>(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMessage(), new DeleteImageResponse(DeleteImageResponseCode.IMAGE_DELETE_SUCCESS)) : new ApiResponse<>(ApiResultCode.FAILED.getCode(), ApiResultCode.FAILED.getMessage(), new DeleteImageResponse(DeleteImageResponseCode.IMAGE_DELETE_FAILED));
    }

    public ApiResponse<CheckImageResponse> insertImage(String url, long poster, long groupid) {
        return checkImageExist(url, poster, groupid) ? new ApiResponse<>(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMessage(), new CheckImageResponse(CheckImageResponseCode.IMAGE_DOWNLOAD_SUCCESS)) : new ApiResponse<>(ApiResultCode.FAILED.getCode(), ApiResultCode.FAILED.getMessage(), new CheckImageResponse(CheckImageResponseCode.IMAGE_DOWNLOAD_FAILED));
    }

    public ApiResponse<RestoreImageResponse> restoreImage(String url) {
        return restoreImageFromBase(url) ? new ApiResponse<>(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMessage(), new RestoreImageResponse(RestoreImageResponseCode.IMAGE_RESTORE_SUCCESS)) : new ApiResponse<>(ApiResultCode.FAILED.getCode(), ApiResultCode.FAILED.getMessage(), new RestoreImageResponse(RestoreImageResponseCode.IMAGE_RESTORE_FAILED));
    }

    public ApiResponse<CheckImageResponse> checkUrl(String url, long poster, long groupid) {
        String imageName = String_Utils.getImageName(url);
        if (isImageExist(imageName)) {
            return new ApiResponse<>(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMessage(), new CheckImageResponse(CheckImageResponseCode.IMAGE_IN_DATABASE));
        } else {
            return insertImageToBase(url, poster, groupid) ? new ApiResponse<>(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMessage(), new CheckImageResponse(CheckImageResponseCode.IMAGE_DOWNLOAD_SUCCESS)) : new ApiResponse<>(ApiResultCode.FAILED.getCode(), ApiResultCode.FAILED.getMessage(), new CheckImageResponse(CheckImageResponseCode.IMAGE_DOWNLOAD_FAILED));
        }
    }


    public ImageResponse getRandomImage() {
        GroupImage groupImage = getRandomImageFromBase();
        String url = groupImage.getUrl();
        if (url_utils.checkUrl(url)) {
            return new ImageResponse(200, 0, url);
        } else {
            return new ImageResponse(200, 1, groupImage.getLocalurl());
        }
    }

    /**
     * @param url 36679969EDDFA003954C05C042787358
     * @return true(存在) false(不存在)
     */
    public boolean isImageExist(String url) {
        QueryWrapper<GroupImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("url", url + "/0?term=2");
        List<GroupImage> groupImages = groupImageDao.selectList(queryWrapper);
        return !groupImages.isEmpty();
    }

    /**
     * 获取随机图片
     *
     * @return 图片url
     */
    public GroupImage getRandomImageFromBase() {
        QueryWrapper<GroupImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isDel", 0);
        queryWrapper.orderByAsc("rand()");
        queryWrapper.last("limit 1");
        return groupImageDao.selectOne(queryWrapper);
    }

    public GroupImage getImageByLocalUrl(String localUrl) {
        QueryWrapper<GroupImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("localurl", localUrl);
        return groupImageDao.selectOne(queryWrapper);
    }

    //TODO 添加图片时，判断图片是否已经存在并删除,并详细定义API返回值
    public boolean insertImageToBase(String url, long poster, long groupid) {
        GroupImage groupImage = new GroupImage();
        groupImage.setUrl(url);
        groupImage.setPoster(poster);
//        groupImage.setLocalurl(url_utils.downloadImage(url, imagepath + String_Utils.getImageName(url), String_Utils.getImageName(url)));
        groupImage.setLocalurl(url_utils.sendImageToOSS(url, String_Utils.getImageName(url)));
        groupImage.setGroupid(groupid);
        groupImage.setIsDel(0);
        if (groupImage.getLocalurl() == null) {
            log.info("图片下载失败，url:{}", url);
            return false;
        }
        return groupImageDao.insert(groupImage) == 1;
    }

    public boolean checkImageExist(String url, long poster, long groupid) {
        if (!isImageExist(url)) {
            return insertImageToBase(url, poster, groupid);
        } else {
            return true;
        }
    }

    public boolean deleteImageFromBase(String url) {
        System.out.println(url);
        String name = String_Utils.getImageName(url);
        QueryWrapper<GroupImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("url", name + "/0?term=2");
        GroupImage groupImage = groupImageDao.selectOne(queryWrapper);
        groupImage.setIsDel(1);
        return groupImageDao.updateById(groupImage) == 1;
    }

    public boolean restoreImageFromBase(String url) {
        String name = String_Utils.getImageName(url);
        QueryWrapper<GroupImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("url", name + "/0?term=2");
        GroupImage groupImage = groupImageDao.selectOne(queryWrapper);
        groupImage.setIsDel(0);
        return groupImageDao.updateById(groupImage) == 1;
    }

    public boolean checkLocalPathExist(String localPath) {
        Path path = Paths.get(imagepath + localPath + ".png");
        return path.toFile().exists();
    }


}
