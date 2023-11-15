package org.xzx.bean.Jx3.jx3pictureRequestBean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Jx3BossTreasurePictureRequest extends Jx3BasicPictureRequest{
    @JsonProperty("server")
    private String server;
    @JsonProperty("name")
    private String name;

    public Jx3BossTreasurePictureRequest(String server, String name, String robot) {
        super(robot);
        this.server = server;
        this.name = name;
    }
}
