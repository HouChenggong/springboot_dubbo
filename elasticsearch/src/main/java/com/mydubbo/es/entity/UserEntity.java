package com.mydubbo.es.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "index_user",type = "user")
@Data
public class UserEntity {
    @Id
    private String id ;

    private String name;

    private String sex;

    private Integer age;
}
