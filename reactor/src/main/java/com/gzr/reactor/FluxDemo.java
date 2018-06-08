package com.gzr.reactor;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author gaozhirong
 * @version ${todo}
 * @Title: ${file_name}
 * @Description: ${todo}
 * @date 2018/6/7 14:15
 */
public class FluxDemo {

    /**
     * just()：可以指定序列中包含的全部元素。创建出来的 Flux 序列在发布这些元素之后会自动结束。
     * fromArray()，fromIterable()和 fromStream()：可以从一个数组、Iterable 对象或 Stream 对象中创建 Flux 对象。
     * empty()：创建一个不包含任何元素，只发布结束消息的序列。
     * error(Throwable error)：创建一个只包含错误消息的序列。
     * never()：创建一个不包含任何消息通知的序列。
     * range(int start, int count)：创建包含从 start 起始的 count 个数量的 Integer 对象的序列。
     * interval(Duration period)和 interval(Duration delay, Duration period)：创建一个包含了从 0 开始递增的 Long 对象的序列。其中包含的元素按照指定的间隔来发布。除了间隔时间之外，还可以指定起始元素发布之前的延迟时间。
     * intervalMillis(long period)和 intervalMillis(long delay, long period)：与 interval()方法的作用相同，只不过该方法通过毫秒数来指定时间间隔和延迟时间。
     *
     * @param args
     */

    public static void main(String[] args) {
        Flux.generate(sink -> {
            sink.next("Hello");
            sink.complete();
        }).subscribe(System.out::println);

        /**
         * next()方法只能最多被调用一次。在有些情况下，序列的生成可能是有状态的，需要用到某些状态对象。
         * 此时可以使用 generate()方法的另外一种形式 generate(Callable<S> stateSupplier, BiFunction<S,SynchronousSink<T>,S> generator)，
         * 其中 stateSupplier 用来提供初始的状态对象。在进行序列生成时，状态对象会作为 generator 使用的第一个参数传入，可以在对应的逻辑中对该状态对象进行修改以供下一次生成时使用。
         */
        final Random random = new Random(47);
        Flux.generate(ArrayList::new, (list, sink) -> {
            int value = random.nextInt(100);
            list.add(value);
            sink.next(value);
            if (list.size() == 10) {
                sink.complete();
            }
            return list;
        }).subscribe(System.out::println);


        /**
         * create方法与generate方法不同之处在于所使用的是FluxSink对象，FluxSInk支持同步和异步消息的产生，并且可以再一次调用产生多个元素
         */
        Flux.create(sink -> {
            for (int i = 0; i < 10; i++) {
                sink.next(i);
            }
            sink.complete();
        }).subscribe(System.out::println);
    }

}
