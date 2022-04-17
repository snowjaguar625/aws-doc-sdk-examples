/*
 * Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.example.ec2;
import software.amazon.awssdk.services.ec2.EC2Client;
import software.amazon.awssdk.services.ec2.model.RebootInstancesRequest;
import software.amazon.awssdk.services.ec2.model.RebootInstancesResponse;

/**
 * Reboots and EC2 instance
 */
public class RebootInstance
{
    public static void main(String[] args)
    {
        final String USAGE =
            "To run this example, supply an instance id\n" +
            "Ex: RebootInstnace <instance_id>\n";

        if (args.length != 1) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String instance_id = args[0];

        EC2Client ec2 = EC2Client.create();

        RebootInstancesRequest request = RebootInstancesRequest.builder()
            .instanceIds(instance_id).build();

        RebootInstancesResponse response = ec2.rebootInstances(request);

        System.out.printf(
            "Successfully rebooted instance %s", instance_id);
    }
}

