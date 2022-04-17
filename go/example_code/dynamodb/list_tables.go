/*
   Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This file is licensed under the Apache License, Version 2.0 (the "License").
   You may not use this file except in compliance with the License. A copy of
   the License is located at

    http://aws.amazon.com/apache2.0/

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
   CONDITIONS OF ANY KIND, either express or implied. See the License for the
   specific language governing permissions and limitations under the License.
*/

    svc := dynamodb.New(session.New(&aws.Config{Region: aws.String("us-west-2")}))
    result, err := svc.ListTables(&dynamodb.ListTablesInput{})
    if err != nil {
        log.Println(err)
        return
    }

    log.Println("Tables:")
    for _, table := range result.TableNames {
        log.Println(*table)
    }