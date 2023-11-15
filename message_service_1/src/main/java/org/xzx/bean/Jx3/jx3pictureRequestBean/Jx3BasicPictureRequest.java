package org.xzx.bean.Jx3.jx3pictureRequestBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jx3BasicPictureRequest {
    private int scale;
    private String robot;
    private int cache;

    public Jx3BasicPictureRequest(String robot) {
        this.robot = robot;
        this.scale = 1;
        this.cache = 0;
    }
}
