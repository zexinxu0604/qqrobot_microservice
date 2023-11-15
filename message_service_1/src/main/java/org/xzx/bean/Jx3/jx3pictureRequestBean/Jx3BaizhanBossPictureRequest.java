package org.xzx.bean.Jx3.jx3pictureRequestBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class Jx3BaizhanBossPictureRequest extends Jx3BasicPictureRequest{

    public Jx3BaizhanBossPictureRequest(String robot) {
        super(robot);
    }
}
