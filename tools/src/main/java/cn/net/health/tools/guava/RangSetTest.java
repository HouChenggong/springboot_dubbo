package cn.net.health.tools.guava;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/24 19:26
 * <p>
 * RangSet其实就是对交集并集差集的处理
 */
public class RangSetTest {
    public static void main(String[] args) {
        RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 10)); // {[1, 10]}
        rangeSet.add(Range.closedOpen(11, 15)); // disconnected range: {[1, 10], [11, 15)}
        rangeSet.add(Range.closedOpen(15, 20)); // connected range; {[1, 10], [11, 20)}
        rangeSet.add(Range.openClosed(0, 0)); // empty range; {[1, 10], [11, 20)}
        rangeSet.remove(Range.open(5, 10)); // splits [1, 10]; {[1, 5], [10, 10], [11, 20)}
        System.out.println(rangeSet.toString());
        System.out.println(rangeSet.contains(3));
        System.out.println(rangeSet.rangeContaining(11));
        System.out.println(rangeSet.encloses(Range.closedOpen(1, 4)));
        System.out.println(rangeSet.span());
    }
}
