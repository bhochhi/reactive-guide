package com.codewithme.bhochhi.webclientdemo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {
    private String CompanyName;
    private String origin;
}
