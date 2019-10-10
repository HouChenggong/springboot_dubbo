package com.mydubbo.common.clone;


import lombok.Data;

import java.io.Serializable;

@Data
public class YourDog  implements Serializable{
    private static final long serialVersionUID = -990702342305081096L;
    private String dogName ;


    public YourDog(YourDog yourDog){
        this.dogName=yourDog.dogName;
    }
    public YourDog(){

    }
}
