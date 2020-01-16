package cn.net.health.user.test.lunxun;

import java.security.SecureRandom;
import java.util.*;

/**
 * @author xiyou
 * @version 1.2
 * @date 2020/1/16 9:47
 */
public class PinFei {
    public static final List<String> PIN_FEI_LIST = Arrays.asList("司理理", "鸡腿姑娘", "芳妃", "灵儿", "皇后");


    /**
     * 带权重的集合
     */
    public static final Map<String, Integer> PRINCESS_MAP = new LinkedHashMap<String, Integer>() {
        {
            put("令妃甄嬛传", 50);
            put("娴", 10);
            put("高贵妃", 30);
            put("纯妃", 20);
        }
    };

    //getPrincess
    public static Integer INDEX = 0;
    //getPrincess1
    public static Integer INDEX2 = 0;
    //getPrincess2
    public static Integer INDEX3 = 0;
    //getPrincess3
    public static Integer INDEX4 = 0;
    //getPrincess3
    public static Map<String, Integer> DONG_TAI = new HashMap<>(16);
    //getPrincess3
    public static Integer totalWeight;

    /**
     * 普通轮询
     *
     * @return
     */
    private static String getPrincess() {
        // 超过数组大小需要归零（每次获取前判断，防止配置变化导致索引越界）
        if (INDEX >= PIN_FEI_LIST.size()) {
            INDEX = 0;
        }
        String princess = PIN_FEI_LIST.get(INDEX);
        INDEX++;
        return princess;
    }

    /**
     * 加权值轮询，用list放入相同元素
     *
     * @return
     */
    private static String getPrincess1() {

        // 遍历map放入到list中
        List<String> princessList = new ArrayList<String>();
        for (String princess : PRINCESS_MAP.keySet()) {
            int weight = PRINCESS_MAP.get(princess);
            // 根据权重值重复放入到一个list中
            for (int i = 0; i < weight; i++) {
                princessList.add(princess);
            }
        }

        if (INDEX2 >= princessList.size()) {
            INDEX2 = 0;
        }
        String princess = princessList.get(INDEX2);

        INDEX2++;

        return princess;
    }

    /**
     * 加权轮询，不用放入相同元素
     *
     * @return
     */
    private static String getPrincess2() {
        //记录总权重值
        int totalWeight = 0;
        for (String princess : PRINCESS_MAP.keySet()) {
            int weight = PRINCESS_MAP.get(princess);
            totalWeight += weight;
        }

        // 归零
        if (INDEX3 >= totalWeight) {
            INDEX3 = 0;
        }

        int index = INDEX3;
        String result = null;
        for (String princess : PRINCESS_MAP.keySet()) {
            int weight = PRINCESS_MAP.get(princess);

            // 落在当前区间 直接返回
            if (index < weight) {

                result = princess;
                break;
            }

            // 没有落在当前区间 继续循环
            index = index - weight;

        }

        INDEX3++;
        return result;
    }

    private static String getPrincess3() {


        if (INDEX4 == 0) {
            //记录总权重值
            totalWeight = 0;
            for (String princess : PRINCESS_MAP.keySet()) {
                int weight = PRINCESS_MAP.get(princess);
                totalWeight += weight;
                DONG_TAI.put(princess, weight);
            }
        }
        //每次重置权重
        DONG_TAI.forEach((key, value) -> {
            int oneValue = PRINCESS_MAP.get(key);
            if (INDEX4 == 0) {
                DONG_TAI.put(key, oneValue);
            } else {
                DONG_TAI.put(key, DONG_TAI.get(key) + oneValue);
            }
        });
        INDEX4++;
        //找到最大权重的值
        int maxWeight = 0;
        String maxWeightName = "";
        for (String key : DONG_TAI.keySet()) {
            Integer oneWeight = DONG_TAI.get(key);
            if (oneWeight >= maxWeight) {
                maxWeight = oneWeight;
                maxWeightName = key;
            }
        }
        DONG_TAI.put(maxWeightName, -totalWeight + maxWeight);
        return maxWeightName;

    }

    /**
     * 还有一种是随机数，把所有的权重加起来，随机到了哪个区间就是哪个
     * 还有hash 取模
     * 还有一致性hash 其实就是把一个⚪分成若干等分
     *
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 11; i++) {
            System.out.println(getPrincess3());
        }
    }
}
