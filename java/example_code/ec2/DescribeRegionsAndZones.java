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
package ec2;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.AvailabilityZone;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import com.amazonaws.services.ec2.model.DescribeRegionsResult;
import com.amazonaws.services.ec2.model.Region;

/**
 * Describes all regions and zones
 */
public class DescribeRegionsAndZones {

    public static void main(String[] args) {

        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

        DescribeRegionsResult response = ec2.describeRegions();

        for(Region region : response.getRegions()) {
            System.out.printf("Found region %s with endpoint %s", region.getRegionName(), region.getEndpoint());
        }

        DescribeAvailabilityZonesResult describeAvailabilityZonesResponse = ec2.describeAvailabilityZones();

        for(AvailabilityZone zone : describeAvailabilityZonesResponse.getAvailabilityZones()) {
            System.out.printf("Found availability zone %s with status %s in region %s",
                              zone.getRegionName(), zone.getState(), zone.getRegionName());
        }
    }
}
