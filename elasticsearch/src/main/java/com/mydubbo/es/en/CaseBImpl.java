package com.mydubbo.es.en;

import org.springframework.stereotype.Component;

@Component
@CaseAnnotation(value = CaseEnum.B)
public class CaseBImpl implements CaseInterface{

    @Override
    public String execute(CaseRequest request) throws Exception {
        return "结果：B -- "+request.getName();
    }
}