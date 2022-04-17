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

package main

import (
	"fmt"
	"os"

	"github.com/aws/aws-sdk-go/aws/session"
	"github.com/aws/aws-sdk-go/service/s3"
)

// Allows person with EMAIL address PERMISSION access to BUCKET OBJECT
// If PERMISSION is missing, they get READ access.
//
// Usage:
//    go run s3_put_bucket_acl.go BUCKET OBJECT EMAIL [PERMISSION]
func main() {
	if len(os.Args) < 4 {
		exitErrorf("Bucket name, object name, and email address required; permission optional (READ if omitted)\nUsage: go run", os.Args[0], "BUCKET OBJECT EMAIL [PERMISSION]")
	}

	bucket := os.Args[1]
	key := os.Args[2]
	address := os.Args[3]

	permission := "READ"

	if len(os.Args) == 5 {
		permission = os.Args[4]

		if !(permission == "FULL_CONTROL" || permission == "WRITE" || permission == "WRITE_ACP" || permission == "READ" || permission == "READ_ACP") {
			fmt.Println("Illegal permission value. It must be one of:")
			fmt.Println("FULL_CONTROL, WRITE, WRITE_ACP, READ, or READ_ACP")
			os.Exit(1)
		}
	}

	user_type := "AmazonCustomerByEmail"

	// Initialize a session that the SDK will use to load configuration,
	// credentials, and region from the shared config file. (~/.aws/config).
	sess := session.Must(session.NewSessionWithOptions(session.Options{
		SharedConfigState: session.SharedConfigEnable,
	}))

	// Create S3 service client
	svc := s3.New(sess)

	// Get existing ACL
	result, err := svc.GetObjectAcl(&s3.GetObjectAclInput{Bucket: &bucket, Key: &key})

	if err != nil {
		exitErrorf(err.Error())
	}

	owner := *result.Owner.DisplayName
	owner_id := *result.Owner.ID

	// Existing grants
	grants := result.Grants

	// Create new grantee to add to grants
	var new_grantee s3.Grantee = s3.Grantee{EmailAddress: &address, Type: &user_type}
	var new_grant s3.Grant = s3.Grant{Grantee: &new_grantee, Permission: &permission}

	// Add them to the grants
	grants = append(grants, &new_grant)

	params := &s3.PutObjectAclInput{
		Bucket: &bucket,
		Key:    &key,
		AccessControlPolicy: &s3.AccessControlPolicy{
			Grants: grants,
			Owner: &s3.Owner{
				DisplayName: &owner,
				ID:          &owner_id,
			},
		},
	}

	// Set bucket ACL
	_, err = svc.PutObjectAcl(params)

	if err != nil {
		exitErrorf(err.Error())
	}

	fmt.Println("Congratulations. You gave user with email address", address, permission, "permission to bucket", bucket, "object", key)
}

func exitErrorf(msg string, args ...interface{}) {
	fmt.Fprintf(os.Stderr, msg+"\n", args...)
	os.Exit(1)
}
