/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.codewithme.bhochhi;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ConcurrencyModel {

    public static void main(String[] args) {

        System.out.println("starting main: on thread:" + Thread.currentThread().getName());
        new ConcurrencyModel().process();
    }

    public static void createSubscribers(Flux<String> flux) {

        System.out.println("---------------------------------createSubscribers called: on thread:" + Thread.currentThread().getName());

        IntStream.range(1, 5).forEach(value ->
                //shows you can use flux multiple times
                flux.subscribe(integer -> System.out.println(value + " consumer processed "
                        + integer + " using thread: " + Thread.currentThread().getName())));

    }


    public void process() {
        /**
         *  By default the execution happens in the thread of the caller to subscribe().
         */
        System.out.println();
        Flux<String> flux0 = Flux.just("A", "B");
        createSubscribers(flux0);


        /**
         * In Reactor the execution context is determined by the used Scheduler,
         * which is an interface and the Schedulers provides implementation with static methods.
         * shows...
         * 1. each element emitted will be subscribed into different thread than main
         * 2. flux can be reuse many time to emit same element.
         * 3. because flux excuting in different thread main thread may complete before flux run.. so to making main tread to wait. using Thread.sleep
         */

        try {
            Flux<String> flux2 = Flux.just("A", "B").delayElements(Duration.ofMillis(0)); //use Schedulers parallel thread when you use delayElements
            createSubscribers(flux2);
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }


        /**
         *There are two ways to change explicitly the execution context (Scheduler) in a reactive pipeline via the publishOn and subscribeOn methods.
         */

        /**
         * using Executor Thread pool.
         * 1. this is not much different that running in the main thread when using singleThread
         * 2. You are responsible for shutdown the thread
         */

        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Flux<String> flux2 = Flux.just("A-executor", "B-executor", "C-executor").publishOn(Schedulers.fromExecutor(executor));
            createSubscribers(flux2);

            executor.shutdown();
            boolean awaitTermination = executor.awaitTermination(500, TimeUnit.MILLISECONDS);
            if (!awaitTermination) {
                executor.shutdownNow();
            }
            System.out.println("continue other stuffs.. with main thread...");
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            Flux<String> flux2 = Flux.just("A-elastic", "B-elastic", "C-elastic").publishOn(Schedulers.elastic());
            createSubscribers(flux2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("...");

//        Using Parallel
        try {
            Flux<String> flux2 = Flux.just("A-parallel", "B-parallel", "C-parallel").publishOn(Schedulers.single());
            createSubscribers(flux2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * With the map operator we create another Flux (flux2) and when we subscribe to this flux2 instance, then flux2 implicitly subscribes to flux1 as well.
         * And when the flux1 starts to emit elements it will call flux2 which will call our Subscriber.
         * We can notice that there is a subscription process and emitting process.
         * In Reactor terminology the flux1 would be the upstream and flux2 the downstream.
         *

         *
         */

        Flux<String> flux1 = Flux.just("foo", "bar");
        Flux<String> flux2 = flux1.map(String::toUpperCase);
        flux2.subscribe(System.out::println);


        /**
         * The publishOn influences the emitting process. It takes an emitted element from the upstream and replays it
         * downstream while executing the callback on a worker from the associated Scheduler.
         * This means it will also affect where the subsequent operators will execute. (until another publishOn is chained in).
         */


        /**
         * The subscribeOn rather influences the subscription process.
         * It doesn’t matter where it is put in the reactive pipeline,
         * it always affects the context of the source emission, and does not affect the behavior of subsequent calls to publishOn.
         * If there are multiple subscribeOn in the chain the earliest subscribeOn call in the chain is actually taken into account.
         */

        try {
            Flux<String> flux3 =
                    Flux.just("A-parallel", "B-parallel")
                            // this is influenced by subscribeOn
                            .doOnNext(s -> System.out.println(s + " before publishOn using thread: " + Thread.currentThread().getName()))
                            .publishOn(Schedulers.elastic())
                            // the rest is influenced by publishOn
                            .doOnNext(s -> System.out.println(s + " after publishOn using thread: " + Thread.currentThread().getName()))
                            .delayElements(Duration.ofMillis(1))
                            .subscribeOn(Schedulers.single());
            createSubscribers(flux3);
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Main Thread is dead now...:" + Thread.currentThread().getName());
    }
}
