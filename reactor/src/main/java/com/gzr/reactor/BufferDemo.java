package com.gzr.reactor;

import reactor.core.publisher.Flux;

/**
 * @author gaozhirong
 * @version ${todo}
 * @Title: ${file_name}
 * @Description: ${todo}
 * @date 2018/6/714:58
 */
public class BufferDemo {

    /**
     * https://www.ibm.com/developerworks/cn/java/j-cn-with-reactor-response-encode/index.html
     * <p>
     * buffer和bufferTimeout作用是把当前流中的元素收集到集合中，并把集合对象作为流中的新元素
     * 收集时可以指定条件 1.所包含的元素的最大数量 2.收集的时间间隔
     * buffer()仅使用一个条件 bufferTimeout()可以同时指定两个条件
     * 指定时间间隔可以用Duration对象或毫秒数 即使用bufferMills()或使用bufferTimeoutMills()两个方法
     * <p>
     * <p>
     * 除了元素数量和时间间隔之外，还可以通过 bufferUntil 和 bufferWhile 操作符来进行收集。这两个操作符的参数是表示每个集合中的元素所要满足的条件的 Predicate 对象。
     * bufferUntil 会一直收集直到 Predicate 返回为 true。使得 Predicate 返回 true 的那个元素可以选择添加到当前集合或下一个集合中；
     * bufferWhile 则只有当 Predicate 返回 true 时才会收集。一旦值为 false，会立即开始下一次收集。
     */
    public static void main(String[] args) {
        Flux.range(1, 100).buffer(20).subscribe(System.out::println);
        //Flux.intervalMillis(100).bufferMillis(1001).take(2).toStream().forEach(System.out::println);
        //bufferUtil一直收集到Predicate返回为true，使用Predicate返回为true的那个元素可以选择添加到当前或者下一个集合中
        Flux.range(1, 10).bufferUntil(i -> i % 2 == 0).subscribe(System.out::println);
        System.out.println("-----------------------------------------------------------------------------------------------");
        //bufferWhile则只有当Predicate返回为true才会收集
        Flux.range(1, 10).bufferWhile(i -> i % 2 == 0).subscribe(System.out::println);
        System.out.println("-----------------------------------------------------------------------------------------------");
        Flux.range(1, 10).filter(i -> i % 2 == 0).subscribe(System.out::println);
        System.out.println("-----------------------------------------------------------------------------------------------");

        /**
         * window操作符类似于buffer window操作符把当前流中的元素收集到另外的Flux序列中。因此返回类型为Flux<Flux<T>>
         * 两行语句的输出结果分别是 5 个和 2 个 UnicastProcessor 字符。这是因为 window 操作符所产生的流中包含的是 UnicastProcessor 类的对象，
         * 而 UnicastProcessor 类的 toString 方法输出的就是 UnicastProcessor 字符。
         */
        Flux.range(1, 100).window(20).subscribe(System.out::println);

        /**
         * zipWith 操作符把当前流中的元素与另外一个流中的元素按照一对一的方式进行合并。在合并时可以不做任何处理，由此得到的是一个元素类型为 Tuple2 的流；
         * 也可以通过一个 BiFunction 函数对合并的元素进行处理，所得到的流的元素类型为该函数的返回值。
         */
        System.out.println("-----------------------------------------------------------------------------------------------");
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"))
                .subscribe(System.out::println);
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"), (s1, s2) -> String.format("%s-%s", s1, s2))
                .subscribe(System.out::println);

        System.out.println("-----------------------------------------------------------------------------------------------");
        /**
         * take 系列操作符用来从当前流中提取元素。提取的方式可以有很多种。

         take(long n)，take(Duration timespan)和 takeMillis(long timespan)：按照指定的数量或时间间隔来提取。
         takeLast(long n)：提取流中的最后 N 个元素。
         takeUntil(Predicate<? super T> predicate)：提取元素直到 Predicate 返回 true。
         takeWhile(Predicate<? super T> continuePredicate)： 当 Predicate 返回 true 时才进行提取。
         takeUntilOther(Publisher<?> other)：提取元素直到另外一个流开始产生元素。
         */
        Flux.range(1, 1000).take(10).subscribe(System.out::println);//1-10
        Flux.range(1, 1000).takeLast(10).subscribe(System.out::println);//991-1000
        Flux.range(1, 1000).takeWhile(i -> i < 10).subscribe(System.out::println);//1-9
        Flux.range(1, 1000).takeUntil(i -> i == 10).subscribe(System.out::println);//1-10

        System.out.println("-----------------------------------------------------------------------------------------------");
        /**
         * reduce 和 reduceWith 操作符对流中包含的所有元素进行累积操作，得到一个包含计算结果的 Mono 序列。累积操作是通过一个 BiFunction 来表示的。
         * 在操作时可以指定一个初始值。如果没有初始值，则序列的第一个元素作为初始值。

         在代码清单 10 中，第一行语句对流中的元素进行相加操作，结果为 5050；第二行语句同样也是进行相加操作，不过通过一个 Supplier 给出了初始值为 200，所以结果为 5250。
         */
        Flux.range(1, 100).reduce((x, y) -> x + y).subscribe(System.out::println);
        Flux.range(1, 100).reduceWith(() -> 200, (x, y) -> x + y).subscribe(System.out::println);

        System.out.println("-----------------------------------------------------------------------------------------------");
        /**
         * merge 和 mergeSequential 操作符用来把多个流合并成一个 Flux 序列。不同之处在于 merge 按照所有流中元素的实际产生顺序来合并，而 mergeSequential 则按照所有流被订阅的顺序，以流为单位进行合并。

         代码清单 11 中分别使用了 merge 和 mergeSequential 操作符。进行合并的流都是每隔 100 毫秒产生一个元素，不过第二个流中的每个元素的产生都比第一个流要延迟 50 毫秒。
         在使用 merge 的结果流中，来自两个流的元素是按照时间顺序交织在一起；而使用 mergeSequential 的结果流则是首先产生第一个流中的全部元素，再产生第二个流中的全部元素。
         */
    }

}
