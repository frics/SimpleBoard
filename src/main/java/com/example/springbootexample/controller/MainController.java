package com.example.springbootexample.controller;

import com.example.springbootexample.service.AwsEc2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")

public class MainController {
    @Autowired
    private AwsEc2Service awsEc2Service;

    @GetMapping
    public String getMain() {
        return "hi it is main";
    }

}
