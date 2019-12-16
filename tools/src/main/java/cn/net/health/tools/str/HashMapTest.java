package cn.net.health.tools.str;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/16 16:08
 */
public class HashMapTest {
    /**
     * 容量测试
     * @param initCapacity 入参容量
     * @return 实际容量
     * @throws Exception
     */
    public static int capacityTest(int initCapacity) throws Exception {

        Map<String, String> map = new HashMap<String, String>(initCapacity);

        // 通过反射获取容量变量capacity,并调用map对象
        Method capacity = map.getClass().getDeclaredMethod("capacity");
        capacity.setAccessible(true);
        Integer realCapacity = (Integer) capacity.invoke(map);
        System.out.println("入参容量为" + initCapacity + "时，实际容量为" + realCapacity);
        return realCapacity;
    }
    /**
     * 临界值测试
     * @throws Exception
     */
    public static void thresholdTest() throws Exception {

        Map<String, String> map = new HashMap<String, String>();

        // 获取map扩容时临界阈值  阈值 = 容量 * 加载因子
        // 默认容量 为16，加载因子 默认为0.75
        Field threshold = map.getClass().getDeclaredField("threshold");
        Field size = map.getClass().getDeclaredField("size");
        Method capacity = map.getClass().getDeclaredMethod("capacity");

        threshold.setAccessible(true);
        size.setAccessible(true);
        capacity.setAccessible(true);

        // 未存放对象时，各项值测试
        System.out.println("start:临界值" + threshold.get(map));
        System.out.println("start:size" + size.get(map));
        System.out.println("start:容量" + capacity.invoke(map));

        // 临界值、容量测试
        for (int i=1; i<26; i++) {
            map.put(String.valueOf(i), i + "**");
            if (i == 11 || i == 12 || i == 13 || i == 23 || i == 24 || i == 25) {
                System.out.println("第" + i + "个对象, size为" + size.get(map) + ", 临界值为" + threshold.get(map) + ", 容量为" + capacity.invoke(map));
            }
        }
    }

    /**
     * 临界值测试
     * @throws Exception
     */
    public static void thresholdTest2() throws Exception {

        Map<String, String> map = new HashMap<String, String>(4);

        // 获取map扩容时临界阈值  阈值 = 容量 * 加载因子
        // 默认容量 为16，加载因子 默认为0.75
        Field threshold = map.getClass().getDeclaredField("threshold");
        Field size = map.getClass().getDeclaredField("size");
        Method capacity = map.getClass().getDeclaredMethod("capacity");

        threshold.setAccessible(true);
        size.setAccessible(true);
        capacity.setAccessible(true);

        // 未存放对象时，各项值测试
        System.out.println("start:临界值" + threshold.get(map));
        System.out.println("start:size" + size.get(map));
        System.out.println("start:容量" + capacity.invoke(map));

        // 临界值、容量测试
        for (int i=1; i<10; i++) {
            map.put(String.valueOf(i), i + "**");

                System.out.println("第" + i + "个对象, size为" + size.get(map) + ", 临界值为" + threshold.get(map) + ", 容量为" + capacity.invoke(map));

        }
    }
    public static void main(String[] args)throws Exception {

        capacityTest(1);
        capacityTest(2);
        capacityTest(3);
        capacityTest(4);
        capacityTest(7);
        capacityTest(21);
        capacityTest(1500000000);
        Map<String, String> map = new HashMap<String, String>( );
        // 通过反射获取容量变量capacity,并调用map对象
        Method capacity = map.getClass().getDeclaredMethod("capacity");
        capacity.setAccessible(true);
        Integer realCapacity = (Integer) capacity.invoke(map);
        System.out.println("入参容量不设置时" +    "时，实际容量为" + realCapacity);
        thresholdTest();
        System.out.println("--------------------");
        thresholdTest2();
    }
}
