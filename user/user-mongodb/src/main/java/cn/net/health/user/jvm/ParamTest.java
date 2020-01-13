package cn.net.health.user.jvm;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author xiyou
 * @version 1.2
 * @date 2020/1/13 11:09
 */
@State(Scope.Benchmark)
public class ParamTest {

    @Param({"1", "2", "3"})
    int testNum;

    @Benchmark
    public String test() {
        return String.valueOf(testNum);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ParamTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
