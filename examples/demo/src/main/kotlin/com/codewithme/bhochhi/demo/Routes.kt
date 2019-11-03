package com.codewithme.bhochhi.demo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

@Configuration
class Routes {

    @Bean
    fun route() = router {
        GET("/route") { ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(BodyInserters.fromValue(arrayOf(1, 2, 3))) }
    }
}


fun streamString() {

    return  Flux.range(0,10)//.delayElements(Duration.ofMillis(500))
//            .subscribeOn(Schedulers.elastic())
            .map({ $it+2 })
}