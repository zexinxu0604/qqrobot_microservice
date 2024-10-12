package org.xzx.bean.Jx3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jx3TiebaGuaRequest {
    private String subclass;
    private String server;
    private int limit;
}
