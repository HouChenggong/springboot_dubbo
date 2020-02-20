package cn.net.health.tools.guava;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/24 19:15
 * 其实就是行和列的转换
 */
public class TableTest {
    public static void main(String[] args) {
        Table<String, String, Integer> weightedGraph = HashBasedTable.create();
        weightedGraph.put("v1", "v2", 4);
        weightedGraph.put("v1", "v3", 20);
        weightedGraph.put("v2", "v3", 5);

        System.out.println(weightedGraph.rowMap());
        System.out.println(weightedGraph.rowKeySet());
        System.out.println(weightedGraph.containsRow("v1"));
        System.out.println(weightedGraph.row("v1")); // returns a Map mapping v2 to 4, v3 to 20
        System.out.println(weightedGraph.column("v3"));
        System.out.println(weightedGraph.columnKeySet());
        System.out.println(weightedGraph.column("v3"));
        System.out.println(weightedGraph.cellSet());
    }
}
