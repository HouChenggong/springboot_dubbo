package com.mydubbo.es.en;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InitCaseBeanMapComponent {

    private static Map<CaseEnum,CaseInterface> processMap=new ConcurrentHashMap<>();

    @Autowired
    private SpringContextUtil springContextUtil;
    @PostConstruct
    public void init() {
        //获取那些带上了注解的 处理实现类
        Map<String,Object> map=springContextUtil.getContext().getBeansWithAnnotation(CaseAnnotation.class);
        System.out.println("此时获取出来的是："+map);

        //添加 case常量值~方法操作逻辑实现类 映射
        for (Object process:map.values()){
            CaseAnnotation annotation=process.getClass().getAnnotation(CaseAnnotation.class);
            processMap.put(annotation.value(),(CaseInterface) process);
        }
    }

    public Map<CaseEnum,CaseInterface> getProcessMap(){
        return processMap;
    }
}
