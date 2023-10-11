package org.xzx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xzx.dao.GroupImageDao;
import org.xzx.pojo.GroupImage;
import org.xzx.utils.String_Utils;
import org.xzx.utils.Url_utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class GroupImageService {

    @Autowired
    private GroupImageDao groupImageDao;

    @Autowired
    private Url_utils url_utils;

    @Value("${qq.group.imagePath}")
    private String imagepath;

    /**
     *
     * @param url 36679969EDDFA003954C05C042787358
     * @return true(存在) false(不存在)
     */
    public boolean isImageExist(String url) {
        QueryWrapper<GroupImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("url", url + "/0?term=2");
        queryWrapper.eq("isDel", 0);
        List<GroupImage> groupImages = groupImageDao.selectList(queryWrapper);
        return !groupImages.isEmpty();
    }

    /**
     * 获取随机图片
     * @return 图片url
     */
    public GroupImage getRandomImage(){
        QueryWrapper<GroupImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isDel", 0);
        queryWrapper.orderByAsc("rand()");
        queryWrapper.last("limit 1");
        return groupImageDao.selectOne(queryWrapper);
    }

    public GroupImage getImageByLocalUrl(String localUrl){
        QueryWrapper<GroupImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("localurl", localUrl);
        return groupImageDao.selectOne(queryWrapper);
    }

    public boolean insertImage(String url, long poster, long groupid){
        GroupImage groupImage = new GroupImage();
        groupImage.setUrl(url);
        groupImage.setPoster(poster);
        groupImage.setLocalurl(String_Utils.getImageName(url));
        groupImage.setGroupid(groupid);
        groupImage.setIsDel(0);
        return groupImageDao.insert(groupImage) == 1;
    }

    public boolean deleteImage(String url){
        System.out.println(url);
        String name = String_Utils.getImageName(url);
        QueryWrapper<GroupImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("url", name + "/0?term=2");
        GroupImage groupImage = groupImageDao.selectOne(queryWrapper);
        groupImage.setIsDel(1);
        return groupImageDao.updateById(groupImage) == 1;
    }

    public boolean restoreImage(String url){
        String name = String_Utils.getImageName(url);
        QueryWrapper<GroupImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("url", name + "/0?term=2");
        GroupImage groupImage = groupImageDao.selectOne(queryWrapper);
        groupImage.setIsDel(0);
        return groupImageDao.updateById(groupImage) == 1;
    }

    public boolean checkLocalPathExist(String localPath){
        Path path = Paths.get(imagepath + localPath + ".png");
        return path.toFile().exists();
    }

    public boolean downloadImageFromUrl(String url, String imageName){
        return url_utils.downloadImage(url, imagepath + imageName);
    }

}
