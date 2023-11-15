package org.xzx.bean.Jx3.jx3pictureRequestBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class Jx3RolePictureRequest extends Jx3BasicPictureRequest{

    private String server;

    private String name;

    private String ticket;

    public Jx3RolePictureRequest(String server, String name, String robot, String ticket) {
        super(robot);
        this.server = server;
        this.ticket = ticket;
        this.name = name;
    }
}
