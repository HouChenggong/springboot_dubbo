package cn.net.health.user.test.lunxun;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author xiyou
 * @version 1.2
 * @date 2020/1/16 18:42
 */
public class TestOrucessWeight {
    // 每个妃子及对应的权重实体
    private static Map<String, PrincessWeight> weightMap = new HashMap<String, PrincessWeight>();
    // 总权重值
    private static int totalWeight = 0;

    //所有妃子集合
    public static final Map<String, Integer> PRINCESS_MAP = new LinkedHashMap<String, Integer>() {
        {
            put("令妃", 5);
            put("娴妃", 1);
            put("高贵妃", 3);
            put("纯妃", 2);
        }
    };


    private static String getPrincess() {
        // 初始化妃子及对应的权重实体
        if (weightMap.isEmpty()) {
            //将配置初始化到map中去
            for (String princess : PRINCESS_MAP.keySet()) {
                // 算法的第一点：初始dynamicWeight为0
                weightMap.put(princess, new PrincessWeight(princess, PRINCESS_MAP.get(princess), 0));
                totalWeight += PRINCESS_MAP.get(princess);
            }
        }

        // 算法的第二点：设置currentWeight=设置weight+currentWeight
        for (PrincessWeight weight : weightMap.values()) {
            weight.setDynamicWeight(weight.getWeight() + weight.getDynamicWeight());
        }

        // 算法的第三点：寻找最大的currentWeight
        PrincessWeight maxPrincessWeight = null;
        for (PrincessWeight weight : weightMap.values()) {
            if (maxPrincessWeight == null || weight.getDynamicWeight() > maxPrincessWeight.getDynamicWeight()) {
                maxPrincessWeight = weight;
            }
        }

        // 算法的第四点：最大的dynamicWeight = dynamicWeight-totalWeight
        maxPrincessWeight.setDynamicWeight(maxPrincessWeight.getDynamicWeight() - totalWeight);

        return maxPrincessWeight.getPrincess();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 11; i++) {
            System.out.println(TestOrucessWeight.getPrincess());
        }
    }
}
