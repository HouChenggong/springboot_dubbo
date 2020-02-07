package cn.net.health.tools.guava;


import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;

public class BloomFilterTest {

    /**
     * todo-xiyou 布隆过滤器
     *
     * @param args
     */
    public static void main(String[] args) {
        BloomFilter<String> stringBloomFilter =
                BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")), 1000, 0.001);
        stringBloomFilter.put("a");
        stringBloomFilter.put("a2");
        stringBloomFilter.put("a2");
        stringBloomFilter.put("a3");
        stringBloomFilter.put("a4");
        stringBloomFilter.put("a5");
        System.out.println(stringBloomFilter.mightContain("a6"));
        System.out.println(stringBloomFilter.mightContain("a"));
    }
}
