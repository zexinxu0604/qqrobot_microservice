package org.xzx.bean.Jx3.jx3pictureRequestBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Jx3TeamRecruitPictureRequest extends Jx3BasicPictureRequest{
    private String server;
    private String keyword;

    public Jx3TeamRecruitPictureRequest(String server, String keyword, String robot) {
        super(robot);
        this.server = server;
        this.keyword = keyword;
    }
}
