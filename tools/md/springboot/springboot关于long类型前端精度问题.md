# SpringBoot中关于long类型返回前端精度丢失问题处理

在项目中，我们可能采取bigint作为数据库主键，Java类中我们一般采用Long类型来映射。对于大数值比如7448009641226720631。数据在服务端好好的，到了前端会发现变成7448009641226720000，造成精度丢失，这样显然是有问题的。

# 解决方法

我们只需要配置一下json配置即可，把所有Long型的字段转成String型即可。(本文是基于springboot2.x，其他版本基本一样)

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author xiyou
 * SpringBoot中关于long类型返回前端精度丢失问题处理
 */
@Configuration
public class LongToStringJsonConfig extends WebMvcConfigurationSupport {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        converters.add(jackson2HttpMessageConverter);
    }
}
```

