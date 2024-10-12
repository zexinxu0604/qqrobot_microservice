package org.xzx.bean.Jx3.Jx3Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jx3TiebaItemResponse extends BaseJx3Response{
    private long id;
    private String zone;
    private String server;
    private String name;
    private String url;
    private String context;
    private long reply;
    private String token;
    private int floor;
    private long time;
}
