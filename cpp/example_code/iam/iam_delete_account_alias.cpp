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
#include <aws/iam/model/DeleteAccountAliasRequest.h>

#include <iostream>

/**
 * Deletes an alias from an AWS account, based on command line input
 */
int main(int argc, char** argv)
{
    if(argc != 2)
    {
        std::cout << "Usage: iam_delete_account_alias <account_alias>" << std::endl;
        return 1;
    }

    Aws::String accountAlias(argv[1]);

    Aws::SDKOptions options;
    Aws::InitAPI(options);

    {
        Aws::IAM::IAMClient iamClient;

        Aws::IAM::Model::DeleteAccountAliasRequest deleteAccountAliasRequest;
        deleteAccountAliasRequest.SetAccountAlias(accountAlias);

        auto deleteAccountAliasOutcome = iamClient.DeleteAccountAlias(deleteAccountAliasRequest);
        if (!deleteAccountAliasOutcome.IsSuccess())
        {
            std::cout << "Error deleting account alias " << accountAlias << ": " <<
            deleteAccountAliasOutcome.GetError().GetMessage() << std::endl;
        }
        else
        {
            std::cout << "Successfully deleted account alias " << accountAlias << std::endl;
        }
    }

    Aws::ShutdownAPI(options);

    return 0;
}



