package com.codewithme.bhochhi;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class SinkHandler {

    public static void main(String[] args) {




        new SinkHandler().fetchNumber();


    }


    private void fetchNumber(){

        //Using sink,
        Flux.interval(Duration.ofSeconds(1)).handle((i,sink)-> {
            try{
                sink.next(i.intValue()+2);
            }catch(Exception e){
                sink.error(new RuntimeException("SININK SINK"));  //sink.error will emitt the error to onError
            }
        })
                .onErrorResume(e->e instanceof ArithmeticException, e-> { // you don't try/catch anything in lambda will be on onError
                    System.out.print("SINK SINK");
                    return Mono.error(e);
                })
                .subscribe(System.out::println)
        ;



        //1
        Flux.interval(Duration.ofSeconds(1)).map(i-> i.intValue() + 2/0)
                .onErrorResume(e->e instanceof ArithmeticException, e-> { // you don't try/catch anything in lambda will be on onError
                    System.out.print("this is original");
                    return Mono.error(e);
                })
                .subscribe(System.out::println)
                ;



        //1


        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
