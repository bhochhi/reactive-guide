package com.codewithme.bhochhi.webclientdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    private HelloService helloService;

    public HelloController(HelloService helloService){

        this.helloService = helloService;
    }
   @GetMapping("/hello")
    public List<String> index() {
        return helloService.sayHello();
    }
}
