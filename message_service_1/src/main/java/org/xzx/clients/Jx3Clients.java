package org.xzx.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("jx3-service")
public interface Jx3Clients {
    @RequestMapping("/getbaizhan")
    String getBaizhan();
}
