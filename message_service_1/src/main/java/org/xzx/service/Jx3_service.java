package org.xzx.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.xzx.bean.Jx3.DTO.Jx3AvatarChiguaRequest;
import org.xzx.bean.Jx3.DTO.Jx3TiebaGuaRequest;
import org.xzx.bean.Jx3.Jx3Response.Jx3PictureUrlResponse;
import org.xzx.bean.Jx3.Jx3Response.Jx3TiebaGuaResponse;
import org.xzx.bean.Jx3.Jx3Response.Jx3TiebaItemResponse;
import org.xzx.bean.Jx3.DTO.Jx3TiebaItemRequest;
import org.xzx.bean.Jx3.jx3pictureRequestBean.*;

import java.io.IOException;
import java.util.List;


@Service
@Log4j2
@RefreshScope
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
            throw new NullPointerException("获取角色奇遇信息时请求路径或参数出现错误，请检查, 以下是服务器返回的信息: " + jx3PictureUrlResponse.getMsg());

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
            throw new NullPointerException("获取招募信息时请求路径或参数出现错误，请检查, 以下是服务器返回的信息: " + jx3PictureUrlResponse.getMsg());
        }
    }

    public Jx3PictureUrlResponse get_black_trade_picture(String key_word) {
        Jx3BossTreasurePictureRequest jx3BossTreasurePictureRequest = new Jx3BossTreasurePictureRequest("", key_word, robot_name);
        Jx3PictureUrlResponse jx3PictureUrlResponse = springRestService.postWithObject(jx3_url + "view/trade/record", headers_with_token_v1, jx3BossTreasurePictureRequest, Jx3PictureUrlResponse.class);
        try {
            return jx3PictureUrlResponse;
        } catch (NullPointerException e) {
            log.error("获取黑市信息失败", e);
            throw new NullPointerException("获取黑市信息时请求路径或参数出现错误，请检查, 以下是服务器返回的信息: " + jx3PictureUrlResponse.getMsg());
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

    public Jx3PictureUrlResponse get_firework_picture(String role_name, String server) {
        Jx3RolePictureRequest jx3RolePictureRequest = new Jx3RolePictureRequest(server, role_name, robot_name, ticket);
        Jx3PictureUrlResponse jx3PictureUrlResponse = springRestService.postWithObject(jx3_url + "view/watch/record", headers_with_token_v2, jx3RolePictureRequest, Jx3PictureUrlResponse.class);
        try {
            return jx3PictureUrlResponse;
        } catch (NullPointerException e) {
            log.error("获取角色烟花信息失败", e);
            return null;
        }
    }

    public Jx3PictureUrlResponse get_firework_picture(String role_name) {
        return get_firework_picture(role_name, "破阵子");
    }

    public Jx3PictureUrlResponse get_daily_picture() {
        return get_daily_picture("破阵子");
    }

    public Jx3PictureUrlResponse get_daily_picture(String server) {
        Jx3DailyPictureRequest jx3DailyPictureRequest = new Jx3DailyPictureRequest(server);
        Jx3PictureUrlResponse jx3PictureUrlResponse = springRestService.postWithObject(jx3_url + "view/active/calendar", headers_with_token_v1, jx3DailyPictureRequest, Jx3PictureUrlResponse.class);
        try {
            return jx3PictureUrlResponse;
        } catch (NullPointerException e) {
            log.error("获取日常失败", e);
            return null;
        }
    }

    public Jx3PictureUrlResponse get_gold_price() {
        return get_gold_price("破阵子");
    }

    /**
     * Retrieves the current gold price for a given server.
     *
     * @param server The name of the server.
     * @return The response containing the gold price information. Returns null if the daily picture request fails.
     */
    public Jx3PictureUrlResponse get_gold_price(String server) {
        Jx3DailyPictureRequest jx3DailyPictureRequest = new Jx3DailyPictureRequest(server, robot_name);
        Jx3PictureUrlResponse jx3PictureUrlResponse = springRestService.postWithObject(jx3_url + "view/trade/demon", headers_with_token_v1, jx3DailyPictureRequest, Jx3PictureUrlResponse.class);
        try {
            return jx3PictureUrlResponse;
        } catch (NullPointerException e) {
            log.error("获取日常失败", e);
            return null;
        }
    }

    public Boolean get_server_open_status(String server) {
        Jx3DailyPictureRequest jx3DailyPictureRequest = new Jx3DailyPictureRequest(server);
        JsonNode jsonNode = springRestService.postWithObject(jx3_url + "data/server/check", jx3DailyPictureRequest, JsonNode.class);
        try {
            return jsonNode.get("data").get("status").asBoolean();
        } catch (NullPointerException e) {
            log.error("获取服务器状态失败", e);
            return null;
        }
    }

    public Boolean get_server_open_status() {
        return get_server_open_status("破阵子");
    }

    public List<Jx3TiebaItemResponse> get_tieba_item_url(String server, String item) throws IOException {
        JsonNode jsonNode = springRestService.postWithObject(jx3_url + "data/tieba/item/records", headers_with_token_v1 ,new Jx3TiebaItemRequest(server, item, 1), JsonNode.class);
        System.out.println(jsonNode.toString());
        if (jsonNode.get("code").asInt() != 200) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(jsonNode.get("data").asText());
        return mapper.readerForListOf(Jx3TiebaItemResponse.class).readValue(jsonNode.get("data"));
    }

    public List<Jx3TiebaItemResponse> get_tieba_item_url(String item) throws IOException {
        return get_tieba_item_url("破阵子", item);
    }

    public List<Jx3TiebaGuaResponse> get_tieba_gua_url(String type) throws IOException {
        JsonNode jsonNode = springRestService.postWithObject(jx3_url + "data/tieba/random", headers_with_token_v1, new Jx3TiebaGuaRequest(type, "-",1), JsonNode.class);
        if (jsonNode.get("code").asInt() != 200) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readerForListOf(Jx3TiebaGuaResponse.class).readValue(jsonNode.get("data"));
    }

    public List<Jx3TiebaGuaResponse> get_tieba_gua_url() throws IOException {
        return get_tieba_gua_url("818");
    }

    public String get_Avatar_chigua(String url) {
        String avater_url = "https://gossip.jx3tool.com/api/gossip_summary";
        String url_id = url.substring(url.lastIndexOf("/") + 1);
        log.info("啃大瓜url_id: " + url_id);
        Jx3AvatarChiguaRequest jx3AvatarChiguaRequest = new Jx3AvatarChiguaRequest(url_id, true, 0, 1, 1);
        JsonNode jsonNode = springRestService.postWithObject(avater_url, jx3AvatarChiguaRequest, JsonNode.class);
        if (jsonNode.get("code").asInt() != 200) {
            return null;
        }
        return jsonNode.get("msg").asText();
    }


    public String get_jx3_role_card(String role_name) {
        return get_jx3_role_card(role_name, "破阵子");
    }

    public String get_jx3_role_card(String server, String role_name) {
        Jx3RolePictureRequest jx3RolePictureRequest = new Jx3RolePictureRequest(server, role_name, robot_name, ticket);
        Jx3PictureUrlResponse jx3PictureUrlResponse = springRestService.postWithObject(jx3_url + "data/show/card", headers_with_token_v2, jx3RolePictureRequest, Jx3PictureUrlResponse.class);
        try {
            return jx3PictureUrlResponse.getData().get("showAvatar").asText();
        } catch (NullPointerException e) {
            log.error("获取角色名片失败", e);
            throw new NullPointerException("获取角色名片时请求路径或参数出现错误，请检查, 以下是服务器返回的信息: " + jx3PictureUrlResponse.getMsg());
        }
    }

    public List<String> get_random_jx3_role_card() {
        return get_random_jx3_role_card("破阵子");
    }

    public List<String>  get_random_jx3_role_card(String server) {
        Jx3RolePictureRequest jx3RolePictureRequest = new Jx3RolePictureRequest(server, "", robot_name, ticket);
        Jx3PictureUrlResponse jx3PictureUrlResponse = springRestService.postWithObject(jx3_url + "data/show/random", headers_with_token_v2, jx3RolePictureRequest, Jx3PictureUrlResponse.class);
        try {
            return List.of(jx3PictureUrlResponse.getData().get("showAvatar").asText(), jx3PictureUrlResponse.getData().get("serverName").asText(), jx3PictureUrlResponse.getData().get("roleName").asText());
        } catch (NullPointerException e) {
            log.error("获取随机角色名片失败", e);
            throw new NullPointerException("获取随机角色名片时请求路径或参数出现错误，请检查, 以下是服务器返回的信息: " + jx3PictureUrlResponse.getMsg());
        }
    }


}
