package com.example.springbootexample.controller;

import com.example.springbootexample.domain.DescribeInstanceDTO;
import com.example.springbootexample.service.AwsEc2Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Reservation;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/ec2")
public class AwsEc2Controller {
    @Autowired
    private AwsEc2Service awsEc2Service;

    @GetMapping("/start")
    public ResponseEntity<String> startEc2(@RequestParam(value = "id") String instanceId) {

        if (awsEc2Service.startInstance(instanceId)) {
            return new ResponseEntity<>("instance is started", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("instance start failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stop")
    public ResponseEntity<String> stopEc2(@RequestParam(value = "id") String instanceId) {

        if (awsEc2Service.stopInstance(instanceId)) {
            return new ResponseEntity<>("instance is stoped", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("instance stop failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status")
    public ResponseEntity<String> statusEc2(@RequestParam(value = "id") String instanceId) {
        String status = awsEc2Service.getInstanceStatus(instanceId);

        if (status != null) {
            return new ResponseEntity<>(status, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("get instance status failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
//    public ResponseEntity<List<DescribeInstanceResponse>> listEc2(@RequestParam(value = "id") String instanceId) {
    public ResponseEntity<List<DescribeInstanceDTO>> listEc2() {
        List<DescribeInstanceDTO> result = awsEc2Service.getInstances();

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @ExceptionHandler(RuntimeException.class)
    public void handleRuntimeException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
