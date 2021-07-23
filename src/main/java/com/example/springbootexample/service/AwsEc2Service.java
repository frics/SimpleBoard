package com.example.springbootexample.service;

import com.example.springbootexample.domain.DescribeInstanceDTO;

import java.util.List;

public interface AwsEc2Service {

    public boolean startInstance(String instanceId);

    public boolean stopInstance(String instanceId);

    public String getInstanceStatus(String instanceId);

    public List<DescribeInstanceDTO> getInstances();

}
