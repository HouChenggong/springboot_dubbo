package cn.net.health.user.jvm;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author xiyou
 * @version 1.2
 * @date 2020/1/13 10:25
 */
//每个方法执行前都进行5次预热执行，每隔1秒进行一次预热操作
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
//来控制实际执行的内容，配置的选项本warmup一样。
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class JMHSample_01_HelloWorld {

    static class Demo {
        int id;
        String name;

        public Demo(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    static List<Demo> demoList;

    static {
        demoList = new ArrayList();
        for (int i = 0; i < 10000; i++) {
            demoList.add(new Demo(i, "test"));
        }
    }

    @Benchmark
    //Mode.AverageTime 平均时间
    @BenchmarkMode(Mode.AverageTime)
    //代表测量的单位，
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void testHashMapWithoutSize() {
        Map map = new HashMap();
        for (Demo demo : demoList) {
            map.put(demo.id, demo.name);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void testHashMap() {
        Map map = new HashMap((int) (demoList.size() / 0.75f) + 1);
        for (Demo demo : demoList) {
            map.put(demo.id, demo.name);
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHSample_01_HelloWorld.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
