package com.codewithme.bhochhi.webclientdemo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Greeting {
    public String name;
    public String title;
    public String body;
}
