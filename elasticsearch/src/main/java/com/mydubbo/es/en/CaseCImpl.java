package com.mydubbo.es.en;

import org.springframework.stereotype.Component;

@Component
@CaseAnnotation(value = CaseEnum.C)
public class CaseCImpl implements CaseInterface{

    @Override
    public String execute(CaseRequest request) throws Exception {
        return "结果：c -- "+request.getName();
    }
}