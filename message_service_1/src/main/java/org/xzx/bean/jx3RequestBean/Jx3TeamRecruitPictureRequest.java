package org.xzx.bean.jx3RequestBean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Jx3TeamRecruitPictureRequest {
    private String server;
    private String keyword;
    private int scale;
    private String robot;
    private int cache;

    public Jx3TeamRecruitPictureRequest(String server, String keyword, String robot) {
        this.server = server;
        this.keyword = keyword;
        this.robot = robot;
        this.scale = 1;
        this.cache = 0;
    }
}
