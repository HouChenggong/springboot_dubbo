package com.mydubbo.es;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.mydubbo.es")
public class ElastaicSearchApplication {
    public static void main(String[] args) {

        SpringApplication.run(ElastaicSearchApplication.class, args);
    }
}
