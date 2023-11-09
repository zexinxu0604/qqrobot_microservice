package org.xzx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xzx.dao.GroupImageDao;
import org.xzx.bean.GroupImage;
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
    public GroupImage getRandomImage() {
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
    public boolean insertImage(String url, long poster, long groupid) {
        GroupImage groupImage = new GroupImage();
        groupImage.setUrl(url);
        groupImage.setPoster(poster);
        groupImage.setLocalurl(url_utils.downloadImage(url, imagepath + String_Utils.getImageName(url), String_Utils.getImageName(url)));
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
            return insertImage(url, poster, groupid);
        } else {
            return true;
        }
    }

    public boolean deleteImage(String url) {
        System.out.println(url);
        String name = String_Utils.getImageName(url);
        QueryWrapper<GroupImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("url", name + "/0?term=2");
        GroupImage groupImage = groupImageDao.selectOne(queryWrapper);
        groupImage.setIsDel(1);
        return groupImageDao.updateById(groupImage) == 1;
    }

    public boolean restoreImage(String url) {
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
