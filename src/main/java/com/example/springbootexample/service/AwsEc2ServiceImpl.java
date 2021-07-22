package com.example.springbootexample.service;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

@Service
public class AwsEc2ServiceImpl implements AwsEc2Service {

    private static Region region = Region.AP_NORTHEAST_2;
    private static Ec2Client ec2 = Ec2Client.builder()
            .credentialsProvider(ProfileCredentialsProvider.create("default"))
            .region(region)
            .build();

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

            if ( !response.hasInstanceStatuses()) {
                return null;
            }
            return response.instanceStatuses().toString();
        } catch (Ec2Exception e) {
            return null;
        }
    }
}
