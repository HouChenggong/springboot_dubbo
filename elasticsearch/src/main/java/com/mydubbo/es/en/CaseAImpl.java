package com.mydubbo.es.en;

import org.springframework.stereotype.Component;

@Component
@CaseAnnotation(value = CaseEnum.A)
public class CaseAImpl implements CaseInterface{

    @Override
    public String execute(CaseRequest request) throws Exception {
        return "结果：A -- "+request.getName();
    }
}