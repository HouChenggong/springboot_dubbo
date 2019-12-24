package cn.net.health.tools.guava;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/24 19:03
 * BiMap 其实BiMap就是反转MAP的key 和value
 */
public class BiMapTest {
    public static void main(String[] args) {
        BiMap<Integer, String> logfileMap = HashBiMap.create();
        logfileMap.put(1, "a.log");
        logfileMap.put(2, "b.log");
        logfileMap.put(3, "c.log");
        System.out.println(">>>>>>:" + logfileMap);
        BiMap<String, Integer> filelogMap = logfileMap.inverse();
        System.out.println("<<<<:" + filelogMap);
        logfileMap.put(4, "d.log");
        System.out.println(">>>>>>::" + logfileMap);
        System.out.println("<<<<:" + filelogMap);
    }
}
