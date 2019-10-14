package com.mydubbo.es.en;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponse<T> {

    private T data;

    private String msg;

    private Integer code;

    public BaseResponse(Integer code) {
        this.code = code;

    }

    public BaseResponse(Integer code, String mmsg) {
        this.code = code;
        this.msg = msg;

    }
}
