package com.codewithme.bhochhi.webclientdemo;


import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Configuration
public class WebClientConfg {

    private static final String BASE_URL = "";
    public static final int TIMEOUT = 1000;

    @Bean
    public WebClient webClientWithTimeout() {
//        final  TcpClient tcpClient = TcpClient
//                .create()
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
//                .doOnConnected(connection -> {
//                    connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, MILLISECONDS));
//                    connection.addHandlerLast(new WriteTimeoutHandler(TIMEOUT, MILLISECONDS));
//                });

        return WebClient.builder()
//                .baseUrl(BASE_URL)
//                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}