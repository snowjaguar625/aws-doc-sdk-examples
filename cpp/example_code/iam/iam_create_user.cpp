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
#include <aws/iam/model/CreateUserRequest.h>
#include <aws/iam/model/CreateUserResult.h>
#include <aws/iam/model/GetUserRequest.h>
#include <aws/iam/model/GetUserResult.h>

#include <iostream>

void CreateUser(const Aws::String& userName)
{
    Aws::IAM::IAMClient iamClient;

    Aws::IAM::Model::GetUserRequest getUserRequest;
    getUserRequest.SetUserName(userName);

    auto getUserOutcome = iamClient.GetUser(getUserRequest);
    if(getUserOutcome.IsSuccess())
    {
        std::cout << "IAM user " << userName << " already exists" << std::endl;
        return;
    }
    else if (getUserOutcome.GetError().GetErrorType() != Aws::IAM::IAMErrors::NO_SUCH_ENTITY)
    {
        std::cout << "Error checking existence of IAM user " << userName << ":" << getUserOutcome.GetError().GetMessage() << std::endl;
        return;
    }

    Aws::IAM::Model::CreateUserRequest createUserRequest;
    createUserRequest.SetUserName(userName);

    auto createUserOutcome = iamClient.CreateUser(createUserRequest);
    if(!createUserOutcome.IsSuccess())
    {
        std::cout << "Error creating IAM user " << userName << ":" << createUserOutcome.GetError().GetMessage() << std::endl;
        return;
    }

    std::cout << "Successfully created IAM user " << userName << std::endl;
}

/**
 * Creates an iam user based on command line input
 */
int main(int argc, char** argv)
{
    if(argc != 2)
    {
        std::cout << "Usage: iam_create_user <user_name>" << std::endl;
        return 1;
    }

    Aws::String userName(argv[1]);

    Aws::SDKOptions options;
    Aws::InitAPI(options);

    CreateUser(userName);

    Aws::ShutdownAPI(options);

    return 0;
}



