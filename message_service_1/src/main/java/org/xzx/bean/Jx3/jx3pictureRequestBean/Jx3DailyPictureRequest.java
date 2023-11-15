package org.xzx.bean.Jx3.jx3pictureRequestBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor

public class Jx3DailyPictureRequest extends Jx3BasicPictureRequest{

    private String server;

    public Jx3DailyPictureRequest(String server, String robot) {
        super(robot);
        this.server = server;
    }
}
