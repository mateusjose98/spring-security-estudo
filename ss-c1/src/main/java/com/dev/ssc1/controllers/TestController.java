package com.dev.ssc1.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping(value = "/test")
    public String test() {
        return "Test";
    }

}
