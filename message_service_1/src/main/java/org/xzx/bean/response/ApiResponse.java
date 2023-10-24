package org.xzx.bean.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ApiResponse<T> implements Serializable {
    private int code;
    private String message;
    private T data;
}
