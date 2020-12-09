package com.codewithme.bhochhi.webclientdemo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    public String personName;
    public String method;
    public String url;


}
