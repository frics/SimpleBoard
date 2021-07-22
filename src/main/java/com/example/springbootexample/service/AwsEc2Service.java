package com.example.springbootexample.service;

public interface AwsEc2Service {

    public boolean startInstance(String instanceId);
    public boolean stopInstance(String instanceId);
    public String  getInstanceStatus(String instanceId);

}
