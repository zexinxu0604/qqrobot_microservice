package org.xzx.bean.jx3RequestBean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Jx3RolePictureRequest {
    @JsonProperty("scale")
    private int scale;

    @JsonProperty("server")
    private String server;

    @JsonProperty("name")
    private String name;

    @JsonProperty("robot")
    private String robot;

    @JsonProperty("cache")
    private int cache;

    @JsonProperty("ticket")
    private String ticket;

    public Jx3RolePictureRequest(String server, String name, String robot, String ticket) {
        this.cache = 0;
        this.robot = robot;
        this.server = server;
        this.ticket = ticket;
        this.name = name;
        this.scale = 1;
    }
}
