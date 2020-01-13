package cn.net.health.user.jvm;

/**
 * @author xiyou
 * @version 1.2
 * @date 2020/1/13 10:20
 */

import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author buhao
 * @version StringBuilderRunner.java, v 0.1 2018-12-25 09:53 buhao
 */
public class StringBuilderRunner2 {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                // 导入要测试的类
                .include(StringConnectBenchmark2.class.getSimpleName())
                // 预热5轮
                .warmupIterations(5)
                // 度量10轮
                .measurementIterations(10)
                .mode(Mode.Throughput)
                .forks(3)
                .build();

        new Runner(opt).run();


    }

}
