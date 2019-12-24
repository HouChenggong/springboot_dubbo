package cn.net.health.tools.guava;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/24 17:50
 * 其实MutliMap其实就是一个   Map<K, List<V>>或Map<K, Set<V>>
 *     所以支持ArrayList LinkedList hashMap treeMap
 * <p>
 * put()
 * putAll()
 * remove
 * removeAll
 * get
 * replaceValues
 */
public class MultiMapTest {

    public static void main(String[] args) {
        // creates a ListMultimap with tree keys and array list values
        ListMultimap<String, Integer> treeListMultimap =
                MultimapBuilder.treeKeys().arrayListValues().build();
        treeListMultimap.put("a", 1);
        treeListMultimap.put("a", 2);
        treeListMultimap.put("a", 3);
        treeListMultimap.put("b", 4);
        treeListMultimap.put("c", 5);

        System.out.println(treeListMultimap.asMap().toString());
        System.out.println(treeListMultimap.get("a"));

        List<Integer> stringList = new ArrayList<>(8);
        stringList.add(1);
        stringList.add(2);
        stringList.add(3);
        stringList.add(5);
        treeListMultimap.putAll("g",stringList);
        System.out.println(treeListMultimap.get("g"));
        System.out.println(treeListMultimap.asMap().toString());
        treeListMultimap.replaceValues("b",stringList);
        System.out.println(treeListMultimap.asMap().toString());
    }
}
