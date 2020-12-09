package com.codewithme.bhochhi;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class DoOnNext {
    public static void main(String[] args) {
        System.out.println("Before Thread: " + Thread.currentThread().getName());
        Flux.range(0, 10)
                .publishOn(Schedulers.elastic())
                .doOnNext(integer -> {
                    try {
//                        Thread.sleep(1000);
                        System.out.println("doOnNext==>" + integer + "...." + Thread.currentThread().getName());
                        integer.intValue();
                    } catch (Exception err) {

                    }

                })
                .log()
                .map(integer -> {
                    System.out.println("map: " + integer + ": Thread: " + Thread.currentThread().getName());

                    return integer;
                })
                .checkpoint("dddd")
                .subscribe(integer -> System.out.println(integer));

        // this is to wait for doOnNext to complete its task
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
