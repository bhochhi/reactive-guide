package com.codewithme.bhochhi.demo

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

@Controller
class Controller {
    @GetMapping(path = ["/numbers"], produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    @ResponseBody
    fun getNumbers(): Flux<Int> {
        Flux.range(0,10)//.delayElements(Duration.ofMillis(500))
                .subscribeOn(Schedulers.elastic())
                .subscribe({println("$it by thread: ${Thread.currentThread().name}")})
        return Flux.range(1, 100)
    }

}