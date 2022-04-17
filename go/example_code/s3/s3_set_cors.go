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
	"flag"
	"fmt"
	"os"
	"strings"

	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/aws/session"
	"github.com/aws/aws-sdk-go/service/s3"
)

// Configures CORS rules for a bucket by setting the allowed
// HTTP methods.
//
// Requires the bucket name, and can also take a space separated
// list of HTTP methods.
//
// Usage:
//    go run s3_set_cors.go -b BUCKET_NAME get put
func main() {
	var bucket string
	// Setup the CLI options and validation
	flag.StringVar(&bucket, "b", "", "Bucket to set CORS on, (required)")
	flag.Parse()
	if len(bucket) == 0 {
		exitErrorf("-b <bucket> Bucket name required")
	}
	methods := filterMethods(flag.Args())

	// Inititalize a session that the SDK will use to load configuration,
	// credentials, and region from the shared config file. (~/.aws/config).
	sess := session.Must(session.NewSessionWithOptions(session.Options{
		SharedConfigState: session.SharedConfigEnable,
	}))

	// Create S3 service client
	svc := s3.New(sess)

	// Create a CORS rule for the bucket
	rule := s3.CORSRule{
		AllowedHeaders: aws.StringSlice([]string{"Authorization"}),
		AllowedOrigins: aws.StringSlice([]string{"*"}),
		MaxAgeSeconds:  aws.Int64(3000),

		// Add HTTP methods CORS request that were specified in the CLI.
		AllowedMethods: aws.StringSlice(methods),
	}

	// Create the parameters for the PutBucketCors API call, add add
	// the rule created to it.
	params := s3.PutBucketCorsInput{
		Bucket: aws.String(bucket),
		CORSConfiguration: &s3.CORSConfiguration{
			CORSRules: []*s3.CORSRule{&rule},
		},
	}

	_, err := svc.PutBucketCors(&params)
	if err != nil {
		// Print the error message
		exitErrorf("Unable to set Bucket %q's CORS, %v", bucket, err)
	}

	// Print the updated CORS config for the bucket
	fmt.Printf("Updated bucket %q CORS for %v\n", bucket, methods)
}

func exitErrorf(msg string, args ...interface{}) {
	fmt.Fprintf(os.Stderr, msg+"\n", args...)
	os.Exit(1)
}

func filterMethods(methods []string) []string {
	filtered := make([]string, 0, len(methods))
	for _, m := range methods {
		v := strings.ToUpper(m)
		switch v {
		case "POST", "GET", "PUT", "PATCH", "DELETE":
			filtered = append(filtered, v)
		}
	}

	return filtered
}
