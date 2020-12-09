package com.codewithme.bhochhi.webclientdemo;

import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.List;

@Repository
public class HelloRepo {

    private final WebClient webClient;

    public HelloRepo(WebClient webClient) {

        this.webClient = webClient;
    }

    public Mono<ReqResMap> fromSource1(final ReqResMap reqResMap){
        return webClient
                .get()
                .uri(reqResMap.getRequestUrl())
                .retrieve()
                .bodyToMono(reqResMap.getResponseType())
                .map(res -> {
                    reqResMap.setResponseBody(res);
                    return reqResMap;
                })
                .log()
                .subscribeOn(Schedulers.boundedElastic())
                ;
    }


    public List<ReqResMap> makeIOCalls(List<ReqResMap> reqResMaps) {
            return Flux.fromStream(reqResMaps.stream()).flatMap(this::fromSource1).collectList().block();
    }
}
