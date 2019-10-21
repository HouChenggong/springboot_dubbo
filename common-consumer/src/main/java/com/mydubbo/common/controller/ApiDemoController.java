package com.mydubbo.common.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.mydubbo.common.entity.WordDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.mydubbo.common.service.ApiDemoService;

/**
 * @author xiyou
 */
@RestController
public class ApiDemoController {


    @Reference(version = "${demo.service.version}")
    private ApiDemoService apiDemoService;



    @GetMapping("/say/{name}")
    public String sayHello(@PathVariable("name") String name) {
        return apiDemoService.sayHello(name);
    }


    @GetMapping("/test/dubbo")
    public String sayHello( ) {
        WordDTO wordDTO =new WordDTO();
        wordDTO.setId((long) 23333);
        wordDTO.setTimestamp((long) 1223343434);
        wordDTO.setUuid("uuid");
        wordDTO.setWord("word");
        return apiDemoService.testError(wordDTO);
    }
}
