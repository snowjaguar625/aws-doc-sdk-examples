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
#include <aws/iam/model/DetachRolePolicyRequest.h>
#include <aws/iam/model/ListAttachedRolePoliciesRequest.h>
#include <aws/iam/model/ListAttachedRolePoliciesResult.h>
#include <iostream>

void DetachRolePolicy(const Aws::String& role_name, const Aws::String& policy_arn)
{
    Aws::IAM::IAMClient iam;

    Aws::IAM::Model::ListAttachedRolePoliciesRequest list_request;
    list_request.SetRoleName(role_name);

    bool done = false;
    bool attached = false;
    while (!done)
    {
        auto list_outcome = iam.ListAttachedRolePolicies(list_request);
        if (!list_outcome.IsSuccess())
        {
            std::cout << "Failed to list attached policies of role " <<
                role_name << ": " << list_outcome.GetError().GetMessage() <<
                std::endl;
            return;
        }

        const auto& policies = list_outcome.GetResult().GetAttachedPolicies();
        attached = std::any_of(
            policies.cbegin(), policies.cend(),
            [=](const Aws::IAM::Model::AttachedPolicy& policy)
        {
            return policy.GetPolicyArn() == policy_arn;
        });
        if (attached)
        {
            break;
        }

        done = !list_outcome.GetResult().GetIsTruncated();
        list_request.SetMarker(list_outcome.GetResult().GetMarker());
    }

    if (!attached)
    {
        std::cout << "Policy " << policy_arn << " is not attached to role " <<
            role_name << std::endl;
        return;
    }

    Aws::IAM::Model::DetachRolePolicyRequest detach_request;
    detach_request.SetRoleName(role_name);
    detach_request.SetPolicyArn(policy_arn);

    auto detach_outcome = iam.DetachRolePolicy(detach_request);
    if (!detach_outcome.IsSuccess())
    {
        std::cout << "Failed to detach policy " << policy_arn << " from role "
            << role_name << ": " << detach_outcome.GetError().GetMessage() <<
            std::endl;
        return;
    }

    std::cout << "Successfully detached policy " << policy_arn << " from role "
        << role_name << std::endl;
}

/**
 * Detaches a policy from a role, based on command line input
 */
int main(int argc, char** argv)
{
    if (argc != 3)
    {
        std::cout << "Usage: detach_role_policy <role_name> <policy_arn>" <<
            std::endl;
        return 1;
    }

    Aws::SDKOptions options;
    Aws::InitAPI(options);
    {
        Aws::String role_name(argv[1]);
        Aws::String policy_arn = argv[2];

        DetachRolePolicy(role_name, policy_arn);
    }Aws::ShutdownAPI(options);
    return 0;
}

