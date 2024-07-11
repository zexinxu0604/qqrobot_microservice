package org.xzx.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xzx.bean.qqGroupBean.GroupInfo;
import org.xzx.bean.qqGroupBean.GroupService;
import org.xzx.service.Gocq_service;
import org.xzx.service.GroupServiceService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class GroupServiceConfig {

    @Autowired
    private Gocq_service gocqService;

    @Autowired
    private GroupServiceService groupServiceService;
    @Bean("GroupList")
    public List<Long> getGroupList() {
        return gocqService.get_group_list().stream().map(GroupInfo::getGroup_id).toList();
    }

//    @Bean("GroupServiceMap")
//    public Map<Long, Map<String, Boolean>> getGroupServiceMap() {
//        List<Long> groupList = getGroupList();
//        Map<Long, Map<String, Boolean>> groupServiceMap = new HashMap<>();
//        for (Long group_id : groupList) {
//            List<GroupServiceEnum> groupServiceList = groupServiceService.selectAllGroupServiceByGroupId(group_id);
//            Map<String, Boolean> serviceMap = new HashMap<>();
//            for (GroupServiceEnum groupService : groupServiceList) {
//                serviceMap.put(groupService.getService_name(), groupService.getStatus() == 1);
//            }
//            groupServiceMap.put(group_id, serviceMap);
//        }
//        return groupServiceMap;
//    }

}
