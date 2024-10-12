package org.xzx.bean.Jx3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jx3AvatarChiguaRequest {
    private String gossip_id;
    private boolean only_thread_author;
    private int temperature;
    private int top_k;
    private int top_p;
}
