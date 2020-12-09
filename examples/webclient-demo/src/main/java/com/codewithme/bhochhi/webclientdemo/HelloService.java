package com.codewithme.bhochhi.webclientdemo;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class HelloService {
    private final HelloRepo helloRepo;

    public HelloService(HelloRepo helloRepo) {

        this.helloRepo = helloRepo;
    }


    public List<String> sayHello() {
        ReqResMap reqResMap = new ReqResMap(Company.class);
        reqResMap.setRequestUrl("https://httpbin.org/get");
        ReqResMap reqResMap2 = new ReqResMap(Person.class);
        reqResMap2.setRequestUrl("https://httpbin.org/anything");
        List<ReqResMap> sources = Arrays.asList(reqResMap,reqResMap2);
        return helloRepo.makeIOCalls(sources).stream()
                .map(toBusinessLogic())
                .collect(Collectors.toList());
    }

    private Function<ReqResMap, String> toBusinessLogic() {
        return reqResMap -> {
            if (reqResMap.getResponseType() == Company.class) {
                return ((Company) reqResMap.getResponseBody()).getOrigin();
            }
            if (reqResMap.getResponseType() == Person.class) {
                return ((Person) reqResMap.getResponseBody()).getUrl();
            }
            return "No Match";
        };
    }
}
