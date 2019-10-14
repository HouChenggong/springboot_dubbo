package com.mydubbo.es.en;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnumController {
    @Autowired
    private InitCaseBeanMapComponent mapComponent;

    @RequestMapping(value = "/switch/case",method = RequestMethod.GET)
    public BaseResponse caseInfo(@RequestParam String type,@RequestParam String name){
        BaseResponse response=new BaseResponse(0);
        try {
            CaseRequest request=new CaseRequest(type,name);

            CaseEnum caseEnum=CaseEnum.valueOf(request.getType());
            CaseInterface caseInterface= mapComponent.getProcessMap().get(caseEnum);
            response.setData(caseInterface.execute(request));
        }catch (Exception e){
            response=new BaseResponse(1,e.getMessage());
        }
        return response;
    }

}
