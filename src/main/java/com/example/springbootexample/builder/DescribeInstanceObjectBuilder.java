package com.example.springbootexample.builder;

import com.example.springbootexample.domain.DescribeInstanceDTO;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.Tag;

import java.util.stream.Collectors;

@Component
public class DescribeInstanceObjectBuilder {

    public DescribeInstanceDTO buildDescribeInstanceDTO(Instance instance) {
        if (instance == null) {
            System.out.println("NULL IS RETURN:");
            return null;
        }

        return new DescribeInstanceDTO()
                .setImageId(instance.imageId())
                .setInstanceId(instance.instanceId())
                .setInstanceType(instance.instanceType().toString())
                .setLaunchTime(instance.launchTime().toString())
                .setPrivateDnsName(instance.privateDnsName())
                .setPrivateIpAddress(instance.privateIpAddress())
                .setPublicDnsName(instance.publicDnsName())
                .setPublicIpAddress(instance.publicIpAddress())
                .setStateName(instance.state().nameAsString())
               // .setTags(instance.tags())
                .setTags(instance.tags().stream().collect(Collectors.toMap(Tag::key, Tag::value)))
                .setCpuOptionsCoreCount(instance.cpuOptions().coreCount())
                .setCpuOptionsThreadPerCore(instance.cpuOptions().threadsPerCore());
    }
}
