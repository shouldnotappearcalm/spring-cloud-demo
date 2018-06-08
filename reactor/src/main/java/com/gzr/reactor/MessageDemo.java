package com.gzr.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author gaozhirong
 * @version ${todo}
 * @Title: ${file_name}
 * @Description: ${todo}
 * @date 2018/6/89:17
 */
public class MessageDemo {

    public static void main(String[] args) {
        /*Flux.just(1, 2).concatWith(Mono.error(new IllegalStateException()))
                .subscribe(System.out::println);//1 2 之后接到Exception
        System.out.println("-----------------------------------------------------------------------------------------------");
*/
        //出现错误默认返回为0

        Flux.just(1, 2).concatWith(Mono.error(new IllegalStateException()))
                .onErrorReturn(0)
                .subscribe(System.out::println);//1 2 0
        System.out.println("-----------------------------------------------------------------------------------------------");

        /**
         * switchOnError()方法来使用另外的流来产生元素。在代码清单 17 中，当出现错误时，将产生 Mono.just(0)对应的流，也就是数字 0。
         */

    }

}
