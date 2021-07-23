package com.example.springbootexample.service;

import com.example.springbootexample.builder.DescribeInstanceObjectBuilder;
import com.example.springbootexample.domain.DescribeInstanceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class AwsEc2ServiceImpl implements AwsEc2Service {

    @Autowired
    DescribeInstanceObjectBuilder describeInstanceObjectBuilder;

    private static Region region = Region.AP_NORTHEAST_2;

    private Ec2Client ec2;

    //Bean이 생성되기전에 PostConstruct가 실행되서 static 처럼 작동한다.
    @PostConstruct
    public void initialize() {
        ec2 = Ec2Client.builder()
                .credentialsProvider(ProfileCredentialsProvider.create("default"))
                .region(region)
                .build();
    }

    @Override
    public boolean startInstance(String instanceId) {
        try {
            StartInstancesRequest request = StartInstancesRequest.builder()
                    .instanceIds(instanceId)
                    .build();

            StartInstancesResponse response = ec2.startInstances(request);

            System.out.println(response);
            return true;
        } catch (Ec2Exception e) {
            return false;
        }
    }

    @Override
    public boolean stopInstance(String instanceId) {
        try {
            StopInstancesRequest request = StopInstancesRequest.builder()
                    .instanceIds(instanceId)
                    .build();

            StopInstancesResponse response = ec2.stopInstances(request);
            System.out.printf("Successfully stopped instance %s", instanceId);

            return true;

        } catch (Ec2Exception e) {
            return false;
        }
    }

    @Override
    public String getInstanceStatus(String instanceId) {
        try {
            DescribeInstanceStatusRequest request = DescribeInstanceStatusRequest.builder()
                    .instanceIds(instanceId)
                    .build();

            DescribeInstanceStatusResponse response = ec2.describeInstanceStatus(request);

            System.out.println(response);
            System.out.println(response.instanceStatuses());
            System.out.println(response.hasInstanceStatuses());

            if (!response.hasInstanceStatuses()) {
                return null;
            }
            return response.instanceStatuses().toString();
        } catch (Ec2Exception e) {
            return null;
        }
    }

    @Override
    public List<DescribeInstanceDTO> getInstances() {

        List<DescribeInstanceDTO> list = new ArrayList<>();

        String nextToken = null;
        try {
            do {
                DescribeInstancesRequest request = DescribeInstancesRequest.builder().nextToken(nextToken).build();
                DescribeInstancesResponse response = ec2.describeInstances(request);

                for (Reservation reservation : response.reservations()) {
                    for (Instance instance : reservation.instances()) {
                        //  DescribeInstanceDTO describeInstanceDTO = describeInstanceObjectBuilder.buildDescribeInstanceDTO(instance);
                        System.out.println(instance.tags());
                        //list.add(describeInstanceObjectBuilder.buildDescribeInstanceDTO(instance));
                    }
                }
                nextToken = response.nextToken();

            } while (nextToken != null);
            return list;

        } catch (Ec2Exception e) {
            throw new RuntimeException();

        }
    }
}
