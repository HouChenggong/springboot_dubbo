package com.mydubbo.es.en;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
/**
 * 我们如何将这些case常量值（枚举值） 以及 对应的方法操作逻辑实现类 一一对应起来呢！
 * 自然而然地，我们想到了采用 Map<String,CaseInterface> 进行映射，所以我们需要在项目启动的过程中，
 * 扫描并将上面那些采用了特定的注解CaseAnnotation注解的实现类 的bean组件获取出来并添加进Map中，
 * 即完成了case常量值 ~ 方法操作实现类的映射。
 *
 *
 *
 * 因此，我们需要手动创建一个用于扫描即将加入spring ioc容器的工具SpringContextUtil，其源代码如下所示：
 */
public class SpringContextUtil implements ApplicationContextAware {
    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    //获取spring应用上下文（容器）-进而准备获取相应的bean
    public ApplicationContext getContext() {
        return context;
    }
}
