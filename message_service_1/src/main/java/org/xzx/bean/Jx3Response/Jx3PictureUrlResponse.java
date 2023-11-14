package org.xzx.bean.Jx3Response;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jx3PictureUrlResponse {
    private int code;
    private String msg;
    private JsonNode data;
    private long time;
}
