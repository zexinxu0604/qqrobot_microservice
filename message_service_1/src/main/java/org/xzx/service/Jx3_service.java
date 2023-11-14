package org.xzx.service;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.xzx.bean.Jx3Response.Jx3PictureUrlResponse;
import org.xzx.bean.jx3RequestBean.Jx3RolePictureRequest;
import org.xzx.bean.jx3RequestBean.Jx3TeamRecruitPictureRequest;


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

    public Jx3PictureUrlResponse get_role_info_picture(String role_name, String server){
        Jx3RolePictureRequest jx3RolePictureRequest = new Jx3RolePictureRequest(server, role_name, robot_name, ticket);
        Jx3PictureUrlResponse jx3PictureUrlResponse = springRestService.postWithObject(jx3_url + "view/role/attribute", headers_with_token ,jx3RolePictureRequest, Jx3PictureUrlResponse.class);
        try{
            return jx3PictureUrlResponse;
        } catch (NullPointerException e){
            log.error("获取角色信息失败", e);
            return null;
        }
    }

    public Jx3PictureUrlResponse get_role_info_picture(String role_name){
        return get_role_info_picture(role_name, "破阵子");
    }

    public Jx3PictureUrlResponse get_team_recruit_picture(String key_word){
        return get_team_recruit_picture(key_word, "破阵子");
    }

    public Jx3PictureUrlResponse get_team_recruit_picture(String key_word, String server){
        Jx3TeamRecruitPictureRequest jx3TeamRecruitPictureRequest = new Jx3TeamRecruitPictureRequest(server, key_word, robot_name);
        Jx3PictureUrlResponse jx3PictureUrlResponse = springRestService.postWithObject(jx3_url + "view/member/recruit", headers_with_token, jx3TeamRecruitPictureRequest, Jx3PictureUrlResponse.class);
        try {
            return jx3PictureUrlResponse;
        } catch (NullPointerException e){
            log.error("获取招募信息失败", e);
            return null;
        }
    }
}
