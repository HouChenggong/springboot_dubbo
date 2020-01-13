package cn.net.health.user.jvm;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author xiyou
 * @version 1.2
 * @date 2020/1/13 11:03
 */
//2.在Main类中直接使用@State作为注解，是Main类直接成为“PropertyHolder”
@State(Scope.Thread)
public class JMHSample_04_DefaultState {
    double x = Math.PI;

    @Benchmark
    public void measure() {
        x++;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHSample_04_DefaultState.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
