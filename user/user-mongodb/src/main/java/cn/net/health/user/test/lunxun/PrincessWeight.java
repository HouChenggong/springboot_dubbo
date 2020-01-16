package cn.net.health.user.test.lunxun;

import lombok.Data;


/**
 * @author xiyou
 * @version 1.2
 * @date 2020/1/16 18:40
 * 权重实体类
 */

@Data
public class PrincessWeight {
    private String princess;
    private Integer weight;
    private Integer dynamicWeight;


    public PrincessWeight(String princess, Integer weight, Integer dynamicWeight) {
        super();
        this.princess = princess;
        this.weight = weight;
        this.dynamicWeight = dynamicWeight;
    }

}
