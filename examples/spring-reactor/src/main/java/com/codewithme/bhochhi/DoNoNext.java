package com.codewithme.bhochhi;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class DoNoNext {
    public static void main(String[] args) {
        System.out.println("Before Thread: "+Thread.currentThread().getName());
        Flux.range(0,10)
                .publishOn(Schedulers.single())
                .doOnNext(integer ->{
                    try{
                        Thread.sleep(1000);
                        System.out.println("doOnNext==>" + integer+"...."+Thread.currentThread().getName());
                        integer.intValue();
                    }catch(Exception err){

                    }

                })
                .log()
                .map(integer -> {
                    System.out.println("map: "+integer+ ": Thread: "+Thread.currentThread().getName());
                    System.out.println();

                    return integer;
                })
                .checkpoint()
                .subscribe(integer -> System.out.println(integer));

    }
}
