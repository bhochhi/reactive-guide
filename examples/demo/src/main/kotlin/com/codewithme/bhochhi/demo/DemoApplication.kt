package com.codewithme.bhochhi.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux
import java.time.Duration

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

@Configuration
class Routes {
    @Bean
    fun route() = router {
        GET("/route") { _ -> ServerResponse.ok().body(fromValue(arrayOf(1, 2, 3))) }
    }

}


@Controller
class Controller {
    @GetMapping(path = ["/numbers"], produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    @ResponseBody
    fun getNumbers(): Flux<Int> {
        Flux.range(0,100).delayElements(Duration.ofMillis(500)).subscribe({println("$it by thread: ${Thread.currentThread().name}")})
        return Flux.range(1, 100)//.delayElements(Duration.ofMillis(2))
    }

}
