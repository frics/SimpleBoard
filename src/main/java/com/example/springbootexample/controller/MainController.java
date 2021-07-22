package com.example.springbootexample.controller;

import com.example.springbootexample.service.AwsEc2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/ec2/start")
    public ResponseEntity<String> startEc2(@RequestParam(value = "id") String instanceId) {

        if (awsEc2Service.startInstance(instanceId)) {
            return new ResponseEntity<>("instance is started", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("instance start failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/ec2/stop")
    public ResponseEntity<String> stopEc2(@RequestParam(value = "id") String instanceId) {

        if (awsEc2Service.stopInstance(instanceId)) {
            return new ResponseEntity<>("instance is stoped", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("instance stop failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/ec2/status")
    public ResponseEntity<String> statusEc2(@RequestParam(value = "id") String instanceId) {
        String status = awsEc2Service.getInstanceStatus(instanceId);

        if (status != null) {
            return new ResponseEntity<>(status, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("get instance status failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
