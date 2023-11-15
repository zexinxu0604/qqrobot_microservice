package org.xzx.service;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.xzx.bean.Jx3.Jx3Response.Jx3PictureUrlResponse;
import org.xzx.bean.Jx3.jx3pictureRequestBean.*;


@Service
@Log4j2
public class Jx3_service {
    @Autowired
    private SpringRestService springRestService;

    @Value("${jx3.token}")
    private String token;

    @Value("${jx3.v2token}")
    private String token_v2;

    @Value("${jx3.basicUrl}")
    private String jx3_url;

    private final String robot_name = "zz二号:zz真是宇宙第一帅啊";

    @Value("${jx3.ticket}")
    private String ticket;

    private HttpHeaders headers_with_token_v1;

    private HttpHeaders headers_with_token_v2;


    @PostConstruct
    public void init() {
        headers_with_token_v1 = new HttpHeaders();
        headers_with_token_v1.set("token", token);

        headers_with_token_v2 = new HttpHeaders();
        headers_with_token_v2.set("token", token_v2);
    }

    public Jx3PictureUrlResponse get_role_info_picture(String role_name, String server) {
        Jx3RolePictureRequest jx3RolePictureRequest = new Jx3RolePictureRequest(server, role_name, robot_name, ticket);
        Jx3PictureUrlResponse jx3PictureUrlResponse = springRestService.postWithObject(jx3_url + "view/role/attribute", headers_with_token_v1, jx3RolePictureRequest, Jx3PictureUrlResponse.class);
        try {
            return jx3PictureUrlResponse;
        } catch (NullPointerException e) {
            log.error("获取角色信息失败", e);
            return null;
        }
    }

    public Jx3PictureUrlResponse get_role_info_picture(String role_name) {
        return get_role_info_picture(role_name, "破阵子");
    }

    public Jx3PictureUrlResponse get_role_luck_picture(String role_name, String server) {
        Jx3RolePictureRequest jx3RolePictureRequest = new Jx3RolePictureRequest(server, role_name, robot_name, ticket);
        Jx3PictureUrlResponse jx3PictureUrlResponse = springRestService.postWithObject(jx3_url + "view/luck/adventure", headers_with_token_v1, jx3RolePictureRequest, Jx3PictureUrlResponse.class);
        try {
            return jx3PictureUrlResponse;
        } catch (NullPointerException e) {
            log.error("获取角色奇遇信息失败", e);
            return null;
        }
    }

    public Jx3PictureUrlResponse get_role_luck_picture(String role_name) {
        return get_role_luck_picture(role_name, "破阵子");
    }


    public Jx3PictureUrlResponse get_team_recruit_picture(String key_word) {
        return get_team_recruit_picture(key_word, "破阵子");
    }

    public Jx3PictureUrlResponse get_team_recruit_picture(String key_word, String server) {
        Jx3TeamRecruitPictureRequest jx3TeamRecruitPictureRequest = new Jx3TeamRecruitPictureRequest(server, key_word, robot_name);
        Jx3PictureUrlResponse jx3PictureUrlResponse = springRestService.postWithObject(jx3_url + "view/member/recruit", headers_with_token_v1, jx3TeamRecruitPictureRequest, Jx3PictureUrlResponse.class);
        try {
            return jx3PictureUrlResponse;
        } catch (NullPointerException e) {
            log.error("获取招募信息失败", e);
            return null;
        }
    }

    public Jx3PictureUrlResponse get_boss_treasure_picture(String key_word) {
        return get_boss_treasure_picture(key_word, "破阵子");
    }

    public Jx3PictureUrlResponse get_boss_treasure_picture(String key_word, String server) {
        Jx3BossTreasurePictureRequest jx3BossTreasurePictureRequest = new Jx3BossTreasurePictureRequest(server, key_word, robot_name);
        Jx3PictureUrlResponse jx3PictureUrlResponse = springRestService.postWithObject(jx3_url + "view/valuables/statistical", headers_with_token_v2, jx3BossTreasurePictureRequest, Jx3PictureUrlResponse.class);
        try {
            return jx3PictureUrlResponse;
        } catch (NullPointerException e) {
            log.error("获取掉落信息失败", e);
            return null;
        }
    }

    public Jx3PictureUrlResponse get_baizhan_boss_picture() {
        Jx3BaizhanBossPictureRequest jx3BaizhanBossPictureRequest = new Jx3BaizhanBossPictureRequest(robot_name);
        Jx3PictureUrlResponse jx3PictureUrlResponse = springRestService.postWithObject(jx3_url + "view/active/monster", headers_with_token_v2, jx3BaizhanBossPictureRequest, Jx3PictureUrlResponse.class);
        try {
            return jx3PictureUrlResponse;
        } catch (NullPointerException e) {
            log.error("获取百战信息失败", e);
            return null;
        }
    }

    public Jx3PictureUrlResponse get_firework_picture(String role_name, String server){
        Jx3RolePictureRequest jx3RolePictureRequest = new Jx3RolePictureRequest(server, role_name, robot_name, ticket);
        Jx3PictureUrlResponse jx3PictureUrlResponse = springRestService.postWithObject(jx3_url + "view/watch/record", headers_with_token_v2, jx3RolePictureRequest, Jx3PictureUrlResponse.class);
        try {
            return jx3PictureUrlResponse;
        } catch (NullPointerException e) {
            log.error("获取角色烟花信息失败", e);
            return null;
        }
    }
    public Jx3PictureUrlResponse get_firework_picture(String role_name){
        return get_firework_picture(role_name, "破阵子");
    }

    public Jx3PictureUrlResponse get_daily_picture(String server){
        Jx3DailyPictureRequest jx3DailyPictureRequest = new Jx3DailyPictureRequest(server);
        Jx3PictureUrlResponse jx3PictureUrlResponse = springRestService.postWithObject(jx3_url + "view/active/current", jx3DailyPictureRequest, Jx3PictureUrlResponse.class);
        try {
            return jx3PictureUrlResponse;
        } catch (NullPointerException e) {
            log.error("获取日常失败", e);
            return null;
        }
    }
    public Jx3PictureUrlResponse get_daily_picture(){
        return get_daily_picture("破阵子");
    }

    public Boolean get_server_open_status(String server){
        Jx3DailyPictureRequest jx3DailyPictureRequest = new Jx3DailyPictureRequest(server);
        JsonNode jsonNode = springRestService.postWithObject(jx3_url + "data/server/check", jx3DailyPictureRequest, JsonNode.class);
        try {
            return jsonNode.get("data").get("status").asBoolean();
        } catch (NullPointerException e) {
            log.error("获取服务器状态失败", e);
            return null;
        }
    }

    public Boolean get_server_open_status(){
        return get_server_open_status("破阵子");
    }

}
