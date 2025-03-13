package org.xzx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xzx.bean.Domain.GroupImage;
import org.xzx.bean.ImageBean.ImageCQ;
import org.xzx.bean.enums.ApiResultCode;
import org.xzx.bean.enums.CheckImageResponseCode;
import org.xzx.bean.enums.DeleteImageResponseCode;
import org.xzx.bean.enums.RestoreImageResponseCode;
import org.xzx.bean.response.*;
import org.xzx.dao.GroupImageDao;
import org.xzx.utils.AliyunOSSUtils;
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

    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;


    public ApiResponse<DeleteImageResponse> deleteImage(ImageCQ imageCQ) {
        return deleteImageFromBase(imageCQ) ? new ApiResponse<>(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMessage(), new DeleteImageResponse(DeleteImageResponseCode.IMAGE_DELETE_SUCCESS)) : new ApiResponse<>(ApiResultCode.FAILED.getCode(), ApiResultCode.FAILED.getMessage(), new DeleteImageResponse(DeleteImageResponseCode.IMAGE_DELETE_FAILED));
    }

    public ApiResponse<DeleteImageResponse> realDeleteImage(ImageCQ imageCQ) {
        String deletedFileName = realDeleteImageFromBase(imageCQ);
        if (deletedFileName == null) {
            return new ApiResponse<>(ApiResultCode.FAILED.getCode(), ApiResultCode.FAILED.getMessage(), new DeleteImageResponse(DeleteImageResponseCode.IMAGE_DELETE_FAILED));
        }
        boolean aliyunOssDeleteResult = aliyunOSSUtils.deleteFromOSS(deletedFileName);
        if (aliyunOssDeleteResult) {
            return new ApiResponse<>(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMessage(), new DeleteImageResponse(DeleteImageResponseCode.IMAGE_DELETE_SUCCESS));
        } else {
            return new ApiResponse<>(ApiResultCode.FAILED.getCode(), ApiResultCode.FAILED.getMessage(), new DeleteImageResponse(DeleteImageResponseCode.IMAGE_DELETE_FAILED));
        }
    }

    public ApiResponse<CheckImageResponse> insertImage(ImageCQ imageCQ) {
        return insertImageToBase(imageCQ) ? new ApiResponse<>(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMessage(), new CheckImageResponse(CheckImageResponseCode.IMAGE_DOWNLOAD_SUCCESS)) : new ApiResponse<>(ApiResultCode.FAILED.getCode(), ApiResultCode.FAILED.getMessage(), new CheckImageResponse(CheckImageResponseCode.IMAGE_DOWNLOAD_FAILED));
    }


    public ApiResponse<RestoreImageResponse> restoreImage(ImageCQ imageCQ) {
        return restoreImageFromBase(imageCQ) ? new ApiResponse<>(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMessage(), new RestoreImageResponse(RestoreImageResponseCode.IMAGE_RESTORE_SUCCESS)) : new ApiResponse<>(ApiResultCode.FAILED.getCode(), ApiResultCode.FAILED.getMessage(), new RestoreImageResponse(RestoreImageResponseCode.IMAGE_RESTORE_FAILED));
    }

    public ApiResponse<CheckImageResponse> checkImageFileName(String file_name) {
        String imageName = String_Utils.getFileName(file_name);
        if (isImageExistByFileName(imageName)) {
            return new ApiResponse<>(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMessage(), new CheckImageResponse(CheckImageResponseCode.IMAGE_IN_DATABASE));
        } else {
            return new ApiResponse<>(ApiResultCode.FAILED.getCode(), ApiResultCode.FAILED.getMessage(), null);
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


    public boolean isImageExistByFileName(String fileName) {
        QueryWrapper<GroupImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("localurl", fileName);
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
    public boolean insertImageToBase(ImageCQ imageCQ) {
        GroupImage groupImage = new GroupImage();
        groupImage.setUrl(imageCQ.getUrl());
        groupImage.setPoster(imageCQ.getPoster());
//        groupImage.setLocalurl(url_utils.downloadImage(url, imagepath + String_Utils.getImageName(url), String_Utils.getImageName(url)));
        groupImage.setLocalurl(url_utils.sendImageToOSS(imageCQ.getUrl(), String_Utils.getFileName(imageCQ.getFile_name())));
        groupImage.setGroupid(imageCQ.getGroup_id());
        groupImage.setIsDel(0);
        if (groupImage.getLocalurl() == null) {
            log.info("图片下载失败，url:{}", imageCQ.getUrl());
            return false;
        }
        return groupImageDao.insert(groupImage) == 1;
    }

    public boolean deleteImageFromBase(ImageCQ imageCQ) {
        String name = String_Utils.getFileName(imageCQ.getFile_name());
        QueryWrapper<GroupImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("localurl", name);
        GroupImage groupImage = groupImageDao.selectOne(queryWrapper);
        groupImage.setIsDel(1);
        return groupImageDao.updateById(groupImage) == 1;
    }


    public boolean restoreImageFromBase(ImageCQ imageCQ) {
        String name = String_Utils.getFileName(imageCQ.getFile_name());
        QueryWrapper<GroupImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("localurl", name);
        GroupImage groupImage = groupImageDao.selectOne(queryWrapper);
        groupImage.setIsDel(0);
        return groupImageDao.updateById(groupImage) == 1;
    }

    public String realDeleteImageFromBase(ImageCQ imageCQ) {
        String name = String_Utils.getFileName(imageCQ.getFile_name());
        QueryWrapper<GroupImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("localurl", name);
        GroupImage groupImage = groupImageDao.selectOne(queryWrapper);
        groupImageDao.deleteById(groupImage.getId());
        return groupImage == null ? null : groupImage.getLocalurl();
    }

    public boolean checkLocalPathExist(String localPath) {
        Path path = Paths.get(imagepath + localPath + ".png");
        return path.toFile().exists();
    }


}
