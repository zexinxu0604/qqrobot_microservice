package org.xzx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xzx.bean.qqGroupBean.GroupService;
import org.xzx.dao.GroupServiceDao;

@Service
@Log4j2
public class GroupServiceService {
    @Autowired
    private GroupServiceDao groupServiceDao;

    public void insertGroupService(long group_id, String service_name) {
        groupServiceDao.insert(new GroupService(group_id, service_name));
    }

    public void closeGroupService(long group_id, String service_name) {
        GroupService groupService = new GroupService(group_id, service_name);
        UpdateWrapper<GroupService> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("group_id", group_id).eq("service_name", service_name).set("status", 0);
        groupServiceDao.update(groupService, updateWrapper);
    }

    public void openGroupService(long group_id, String service_name) {
        GroupService groupService = new GroupService(group_id, service_name);
        UpdateWrapper<GroupService> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("group_id", group_id).eq("service_name", service_name).set("status", 1);
        groupServiceDao.update(groupService, updateWrapper);
    }
}
