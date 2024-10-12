package org.xzx.bean.Jx3.Jx3Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jx3TiebaGuaResponse extends BaseJx3Response{
    private long id;
    private String subclass;
    private String zone;
    private String server;
    private String name;
    private String title;
    private long url;
    private String date;
}
