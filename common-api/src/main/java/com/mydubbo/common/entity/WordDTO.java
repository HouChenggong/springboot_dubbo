package com.mydubbo.common.entity;

import lombok.Data;

@Data
public class WordDTO extends BaseDTO {


    private static final long serialVersionUID = -4951914952350736314L;
    private Long id;
    private String uuid;
    private Long timestamp;
    private String word;
}
