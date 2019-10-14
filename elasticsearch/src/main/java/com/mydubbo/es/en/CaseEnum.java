package com.mydubbo.es.en;


import lombok.Getter;

@Getter
public enum CaseEnum {
    A("A"),
    B("B"),
    C("C"),
    ;

    private String type;

    CaseEnum(String type) {
        this.type = type;
    }



}
