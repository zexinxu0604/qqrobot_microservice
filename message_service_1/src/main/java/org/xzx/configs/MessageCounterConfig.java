package org.xzx.configs;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xzx.bean.messageUtil.MessageCounter;
import org.xzx.bean.qqGroupBean.GroupInfo;
import org.xzx.service.Gocq_service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@Log4j2
public class MessageCounterConfig {

    @Autowired
    private Gocq_service gocqService;
    @Bean
    public Map<Integer, MessageCounter> messageCounterMap(){
        List<GroupInfo> groupInfoList = gocqService.get_group_list();
        log.info("群列表：{}", groupInfoList);
        Map<Integer, MessageCounter> messageCounterMap = new ConcurrentHashMap<>();
        for (GroupInfo groupInfo : groupInfoList) {
            messageCounterMap.put(groupInfo.getGroup_id(), new MessageCounter(groupInfo.getGroup_id(), 0, 20));
        }
        return messageCounterMap;
    }
}
