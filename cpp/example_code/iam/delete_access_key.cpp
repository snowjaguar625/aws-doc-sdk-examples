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
#include <aws/iam/IAMClient.h>
#include <aws/iam/model/DeleteAccessKeyRequest.h>
#include <iostream>

/**
 * Deletes an access key from an IAM user, based on command line input
 */
int main(int argc, char** argv)
{
    if (argc != 3)
    {
        std::cout << "Usage: delete_access_key <user_name> <access_key_id>"
            << std::endl;
        return 1;
    }

    Aws::SDKOptions options;
    Aws::InitAPI(options);
    {
        Aws::String user_name(argv[1]);
        Aws::String key_id(argv[2]);

        Aws::IAM::IAMClient iam;

        Aws::IAM::Model::DeleteAccessKeyRequest request;
        request.SetUserName(user_name);
        request.SetAccessKeyId(key_id);

        auto outcome = iam.DeleteAccessKey(request);

        if (!outcome.IsSuccess())
        {
            std::cout << "Error deleting access key " << key_id << " from user "
                << user_name << ": " << outcome.GetError().GetMessage() <<
                std::endl;
        }
        else
        {
            std::cout << "Successfully deleted access key " << key_id
                << " for IAM user " << user_name << std::endl;
        }
    }
    Aws::ShutdownAPI(options);
    return 0;
}

