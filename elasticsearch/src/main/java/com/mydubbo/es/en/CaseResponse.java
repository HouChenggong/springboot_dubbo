package com.mydubbo.es.en;

import lombok.Data;

@Data
public class CaseResponse<T> {

    private T result;
}
