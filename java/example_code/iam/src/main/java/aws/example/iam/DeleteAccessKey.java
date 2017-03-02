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
package iam.src.main.java.aws.example.iam;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.DeleteAccessKeyRequest;
import com.amazonaws.services.identitymanagement.model.DeleteAccessKeyResult;

/**
 * Deletes an access key from an IAM user
 */
public class DeleteAccessKey {

    public static void main(String[] args) {

        final String USAGE =
            "To run this example, supply a username and access key id\n" +
            "Ex: DeleteAccessKey <username> <access-key-id>\n";

        if (args.length != 2) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String username = args[0];
        String accessKey = args[1];

        final AmazonIdentityManagement iam = AmazonIdentityManagementClientBuilder.defaultClient();

        DeleteAccessKeyRequest request = new DeleteAccessKeyRequest()
            .withAccessKeyId(accessKey)
            .withUserName(username);

        DeleteAccessKeyResult response = iam.deleteAccessKey(request);

        System.out.println("Successfully deleted access key " + accessKey + " from user " + username);
    }
}
