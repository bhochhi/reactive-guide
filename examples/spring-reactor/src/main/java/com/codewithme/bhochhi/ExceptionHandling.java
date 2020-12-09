package com.codewithme.bhochhi;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ExceptionHandling {


    public static void main(String[] args) {
        System.out.println("==>Running main in thread: "+Thread.currentThread().getName());

        ExceptionHandling instance = new ExceptionHandling();

        /**
         * how pipeline can catch the error downstream throws various way
         */

/** 1. following why will not work when error is thrown from readDB, will not be caught by .onErrorResume
        instance.readDB()
                .onErrorResume(throwable -> {
                    System.out.println("This will not execute if readDB throw exception in the same thread");
                    return Mono.just("From onErrorResume");
                }).subscribe(System.out::println);
 */
/** 2. To catch such exception you can use with callable...
        Mono.fromCallable(() -> {
            System.out.println("==>Running fromCallable in thread: "+Thread.currentThread().getName());
            return instance.readDB();
//                    .flatMap(ee->Mono.just("from callable"));
        }).onErrorResume(throwable -> {
            System.out.println(".onErrorResume with Mono.fromCallable able to catch exception throw from readDB ");
            return Mono.just(Mono.just("From onErrorResume"));
        }).subscribe(System.out::println);
**/
/** 3. you can also catch this why
 *
 */
    Mono.just("something coming")
//            .publishOn(Schedulers.boundedElastic())
            .flatMap(s->instance.readDB())
            .onErrorResume(throwable -> {
                System.out.println("This will not execute if readDB throw exception in the same thread");
                return Mono.just("From onErrorResume");
            }).subscribe(System.out::println);
        

    }
    private Mono<String> readDB() {
//        System.out.println("==>Running readDB in thread: "+Thread.currentThread().getName());

        throw new RuntimeException("ole way of throwing...");

    }


    private String get() {
//        return "hello world";
        throw new RuntimeException("SOmething went wrong");
    }

    private void tryCatch() {

        /***
         *
         * why try/catch?
         */
        //1. downstream is executed in the same thread.
        try {
            Mono.just("call service")
                    .flatMap(val -> {
                        return callService(val);
                    })
                    .subscribe(System.out::println);
        } catch (Exception e) {

        }
    }

    private Mono<String> callService(String val) {
        //no issue
        return Mono.just("return value from call service");
    }

}
