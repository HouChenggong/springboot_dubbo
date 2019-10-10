package com.mydubbo.common.clone;

import lombok.Data;

import java.io.Serializable;

@Data
public class Person implements Serializable {
   private static final long serialVersionUID = 7512746184754731714L;
   private YourDog dog;
   private String name ;
}

