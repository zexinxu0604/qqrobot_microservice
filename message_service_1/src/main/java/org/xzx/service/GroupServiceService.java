package org.xzx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xzx.bean.enums.GroupServiceEnum;
import org.xzx.bean.Domain.GroupService;
import org.xzx.dao.GroupServiceDao;

import java.util.List;

@Service
@Log4j2
public class GroupServiceService {
    @Autowired
    private GroupServiceDao groupServiceDao;

    @Autowired
    private Gocq_service gocqService;

    public boolean insertGroupService(long group_id, String service_name) {
        return groupServiceDao.insert(new GroupService(group_id, service_name, 0)) == 1;
    }

    public boolean closeGroupService(long group_id, String service_name) {
        GroupService groupService = new GroupService(group_id, service_name);
        UpdateWrapper<GroupService> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("group_id", group_id).eq("service_name", service_name).set("status", 0);
        return groupServiceDao.update(groupService, updateWrapper) == 1;
    }

    public boolean openGroupService(long group_id, String service_name) {
        GroupService groupService = new GroupService(group_id, service_name);
        UpdateWrapper<GroupService> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("group_id", group_id).eq("service_name", service_name).set("status", 1);
        return groupServiceDao.update(groupService, updateWrapper) == 1;
    }

    public List<GroupService> selectAllGroupServiceByServiceName(String service_name) {
        QueryWrapper<GroupService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("service_name", service_name);
        return groupServiceDao.selectList(queryWrapper);
    }

    public List<GroupService> selectAllGroupServiceByGroupId(long group_id) {
        QueryWrapper<GroupService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id", group_id);
        return groupServiceDao.selectList(queryWrapper);
    }

    public List<GroupService> selectAllGroupServiceByGroupIdAndServiceName(long group_id, String service_name) {
        QueryWrapper<GroupService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id", group_id).eq("service_name", service_name);
        return groupServiceDao.selectList(queryWrapper);
    }

    public GroupService selectGroupServiceByGroupIdAndServiceName(long group_id, String service_name) {
        QueryWrapper<GroupService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id", group_id).eq("service_name", service_name);
        return groupServiceDao.selectOne(queryWrapper);
    }

    public boolean checkServiceStatus(long group_id, GroupServiceEnum groupServiceEnum) {
        GroupService groupService = selectGroupServiceByGroupIdAndServiceName(group_id, groupServiceEnum.getServiceName());
        if (groupService == null) {
            insertGroupService(group_id, groupServiceEnum.getServiceName());
            return true;
        }
        if (groupService.getStatus() == 0) {
            return false;
        }
        return true;
    }

    public List<Long> selectAllGroupByServiceName(String service_name) {
        QueryWrapper<GroupService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("service_name", service_name).eq("status", 1);
        List<GroupService> groupServiceList = groupServiceDao.selectList(queryWrapper);
        return groupServiceList.stream().map(GroupService::getGroup_id).toList();
    }
}
