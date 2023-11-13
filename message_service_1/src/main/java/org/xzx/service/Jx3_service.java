package org.xzx.service;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.xzx.bean.jx3RequestBean.Jx3RolePictureRequest;


@Service
@Log4j2
public class Jx3_service {
    @Autowired
    private SpringRestService springRestService;

    @Value("${jx3.token}")
    private String token;

    @Value("${jx3.basicUrl}")
    private String jx3_url;

    private final String robot_name = "zz二号:zz真是宇宙第一帅啊";

    @Value("${jx3.ticket}")
    private String ticket;

    private HttpHeaders headers_with_token;

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void init(){
        headers_with_token = new HttpHeaders();
        headers_with_token.set("token", token);
    }

    public String get_role_info_picture(String role_name, String server){
        Jx3RolePictureRequest jx3RolePictureRequest = new Jx3RolePictureRequest(server, role_name, robot_name, ticket);
        JsonNode jsonNode = springRestService.postWithObject(jx3_url + "view/role/attribute", headers_with_token ,jx3RolePictureRequest, JsonNode.class);
        System.out.println(jsonNode);
        try{
            return jsonNode.get("data").get("url").asText();
        } catch (NullPointerException e){
            log.error("获取角色信息失败", e);
            return null;
        }
    }

    public String get_role_info_picture(String role_name){
        return get_role_info_picture(role_name, "破阵子");
    }
}
