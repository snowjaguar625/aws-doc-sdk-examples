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
	"github.com/aws/aws-sdk-go/service/cloudwatch"
)

// Usage:
// go run main.go <alarm name>
func main() {
	// Load session from shared config.
	sess := session.Must(session.NewSessionWithOptions(session.Options{
		SharedConfigState: session.SharedConfigEnable,
	}))

	// Create new cloudwatch client.
	svc := cloudwatch.New(sess)

	// This will disable the alarm.
	result, err := svc.DisableAlarmActions(&cloudwatch.DisableAlarmActionsInput{
		AlarmNames: []*string{
			&os.Args[1],
		},
	})

	if err != nil {
		fmt.Println("Error", err)
		return
	}

	fmt.Println("Success", result)
}
