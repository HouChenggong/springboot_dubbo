package com.mydubbo.common.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author xiyou
 */
@Getter
@Setter
public class UserCommon implements Serializable {

    private Integer id;

    private String name;

    private Integer age;


}
