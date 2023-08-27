package org.xzx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xzx.dao.GroupImageDao;
import org.xzx.pojo.GroupImage;

import java.util.List;

@Service
public class GroupImageService {

    @Autowired
    private GroupImageDao groupImageDao;

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
}
