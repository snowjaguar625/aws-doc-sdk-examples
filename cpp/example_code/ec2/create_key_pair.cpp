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
#include <aws/ec2/model/CreateKeyPairRequest.h>
#include <aws/ec2/model/CreateKeyPairResponse.h>
#include <iostream>

/**
 * Creates an ec2 key pair based on command line input
 */
int main(int argc, char** argv)
{
    if (argc != 2)
    {
        std::cout << "Usage: create_key_pair <key_pair_name>" << std::endl;
        return 1;
    }

    Aws::SDKOptions options;
    Aws::InitAPI(options);
    {
        Aws::String pair_name = argv[1];

        Aws::EC2::EC2Client ec2;
        Aws::EC2::Model::CreateKeyPairRequest request;
        request.SetKeyName(pair_name);

        auto outcome = ec2.CreateKeyPair(request);
        if (!outcome.IsSuccess())
        {
            std::cout << "Failed to create key pair:" <<
                outcome.GetError().GetMessage() << std::endl;
        }
        else
        {
            std::cout << "Successfully created key pair named " <<
                pair_name << std::endl;
        }
    }
    Aws::ShutdownAPI(options);
    return 0;
}

