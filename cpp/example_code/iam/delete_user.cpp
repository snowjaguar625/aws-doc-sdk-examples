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
#include <aws/iam/model/DeleteUserRequest.h>
#include <aws/iam/model/GetUserRequest.h>
#include <aws/iam/model/GetUserResult.h>
#include <iostream>

void DeleteUser(const Aws::String& user_name)
{
    Aws::IAM::IAMClient iam;

    Aws::IAM::Model::GetUserRequest gu_req;
    gu_req.SetUserName(user_name);

    auto gu_out = iam.GetUser(gu_req);
    if (!gu_out.IsSuccess()) {
        if (gu_out.GetError().GetErrorType() ==
                Aws::IAM::IAMErrors::NO_SUCH_ENTITY) {
            std::cout << "IAM user " << user_name << " does not exist" <<
                std::endl;
        } else {
            std::cout << "Error checking existence of IAM user " << user_name <<
                ": " << gu_out.GetError().GetMessage() << std::endl;
        }
        return;
    }

    Aws::IAM::Model::DeleteUserRequest request;
    request.SetUserName(user_name);

    auto outcome = iam.DeleteUser(request);
    if(!outcome.IsSuccess()) {
        std::cout << "Error deleting IAM user " << user_name << ": " <<
            outcome.GetError().GetMessage() << std::endl;
        return;
    }
    std::cout << "Successfully deleted IAM user " << user_name << std::endl;
}

/**
 * Deletes an IAM user based on command line input; only works for users with no
 * associated resources (groups, policies, etc...) To delete a user in the
 * non-trivial case, use the DeleteUser operation within the
 * aws-cpp-sdk-access-management high level sdk
 */
int main(int argc, char** argv)
{
    if(argc != 2) {
        std::cout << "Usage: iam_delete_user <user_name>" << std::endl;
        return 1;
    }

    Aws::String user_name(argv[1]);

    Aws::SDKOptions options;
    Aws::InitAPI(options);

    DeleteUser(user_name);

    Aws::ShutdownAPI(options);

    return 0;
}

