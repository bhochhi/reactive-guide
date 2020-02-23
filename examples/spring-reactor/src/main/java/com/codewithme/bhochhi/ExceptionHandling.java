package com.codewithme.bhochhi;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ExceptionHandling {


    public static void main(String[] args) {
        Mono<String> value = new ExceptionHandling().readDB();
        value
                .onErrorResume(throwable -> throwable instanceof RuntimeException, throwable -> Mono.just("Recover Hello world"))
                .subscribe(System.out::println);



    }

    private Mono<String> readDB() {
//        try {
//            return Mono.just(get())
//                    .onErrorResume(throwable -> Mono.error(throwable));

        return Mono.just("")
                .publishOn(Schedulers.single())
                .map(d->get())
                    .onErrorResume(throwable -> Mono.error(throwable));


//            return Mono.just("hello")
//                    .map(s -> get())
//                    .onErrorResume(throwable -> Mono.error(throwable));

//        } catch (Exception e) {
//            return Mono.error(e);
//        }
    }

    private String get() {
//        return "hello world";
        throw new RuntimeException("SOmething went wrong");
    }

}
