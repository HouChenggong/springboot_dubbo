package cn.net.health.tools.guava;

import com.google.common.collect.HashMultiset;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/24 17:43
 * 官方地址：https://github.com/google/guava/wiki/NewCollectionTypesExplained#multiset
 * 支持hashMap treeMap linkedHashMap
 */
public class MultiSetTest {

    /**
     * 　　Multiset主要方法
     * <p>
     * 　　Multiset接口定义的接口主要有：
     * 　　　　add(E element) :向其中添加单个元素
     * 　　　　add(E element,int occurrences) : 向其中添加指定个数的元素
     * 　　　　count(Object element) : 返回给定参数元素的个数
     * 　　　　remove(E element) : 移除一个元素，其count值 会响应减少
     * 　　　　remove(E element,int occurrences): 移除相应个数的元素
     * 　　　　elementSet() : 将不同的元素放入一个Set中
     * 　　　　entrySet(): 类似与Map.entrySet 返回Set<Multiset.Entry>。包含的Entry支持使用getElement()和getCount()
     * 　　　　setCount(E element ,int count): 设定某一个元素的重复次数
     * 　　　　setCount(E element,int oldCount,int newCount): 将符合原有重复个数的元素修改为新的重复次数
     * 　　　　retainAll(Collection c) : 保留出现在给定集合参数的所有的元素
     * 　　　　removeAll(Collectionc) : 去除出现给给定集合参数的所有的元素
     *
     * @param args
     */
    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>(8);
        stringList.add("a");
        stringList.add("a");
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");
        stringList.add("d");
        HashMultiset<Object> multiSet = HashMultiset.create();
        multiSet.addAll(stringList);
        System.out.println(multiSet.count("a"));
        multiSet.add("a");
        multiSet.setCount("d", 5);
        System.out.println(multiSet.count("a"));
        multiSet.elementSet().forEach(one -> {
            System.out.println(one + "size:" + multiSet.count(one));
        });
    }


}
