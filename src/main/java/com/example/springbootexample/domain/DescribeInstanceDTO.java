package com.example.springbootexample.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class DescribeInstanceDTO {
    private String imageId;

    private String instanceId;

    private String instanceType;

    private String launchTime;

    private String privateDnsName;

    private String privateIpAddress;

    private String publicDnsName;

    private String publicIpAddress;
    //state에서 Name 분리해야한다.
    private String stateName;

    private Map<String, String> Tags;
    //public List<Tag> Tags;

    private int cpuOptionsCoreCount;

    private int cpuOptionsThreadPerCore;


}
