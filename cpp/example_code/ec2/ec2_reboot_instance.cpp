/*
   Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This file is licensed under the Apache License, Version 2.0 (the "License").
   You may not use this file except in compliance with the License. A copy of
   the License is located at

    http://aws.amazon.com/apache2.0/

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
   CONDITIONS OF ANY KIND, either express or implied. See the License for the
   specific language governing permissions and limitations under the License.
*/
#include <aws/core/Aws.h>

#include <aws/ec2/EC2Client.h>
#include <aws/ec2/model/RebootInstancesRequest.h>

#include <iostream>

void RebootInstance(const Aws::String& instanceId)
{
    Aws::EC2::EC2Client ec2_client;

    Aws::EC2::Model::RebootInstancesRequest rebootInstancesRequest;
    rebootInstancesRequest.AddInstanceIds(instanceId);
    rebootInstancesRequest.SetDryRun(true);

    auto rebootInstancesDryRunOutcome = ec2_client.RebootInstances(rebootInstancesRequest);
    assert(!rebootInstancesDryRunOutcome.IsSuccess());
    if(rebootInstancesDryRunOutcome.GetError().GetErrorType() != Aws::EC2::EC2Errors::DRY_RUN_OPERATION)
    {
        std::cout << "Failed dry run to reboot instance " << instanceId << ": " << rebootInstancesDryRunOutcome.GetError().GetMessage() << std::endl;
        return;
    }

    rebootInstancesRequest.SetDryRun(false);
    auto rebootInstancesOutcome = ec2_client.RebootInstances(rebootInstancesRequest);
    if(!rebootInstancesOutcome.IsSuccess())
    {
        std::cout << "Failed to reboot instance " << instanceId << ": " << rebootInstancesOutcome.GetError().GetMessage() << std::endl;
    }
    else
    {
        std::cout << "Successfully rebooted instance " << instanceId << std::endl;
    }
}

/**
 * Reboots an ec2 instance based on command line input
 */
int main(int argc, char** argv)
{
    if(argc != 2)
    {
        std::cout << "Usage: ec2_reboot_instance <instance_id>" << std::endl;
        return 1;
    }

    Aws::String instanceId = argv[1];

    Aws::SDKOptions options;
    Aws::InitAPI(options);

    RebootInstance(instanceId);

    Aws::ShutdownAPI(options);

    return 0;
}



