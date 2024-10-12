package org.xzx.bean.Jx3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Jx3TiebaItemRequest {
    private String server;
    private String name;
    private int limit;
}
