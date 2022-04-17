<?php
/**
 * Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * This file is licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License. A copy of
 * the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
require 'vendor/autoload.php';

use Aws\CloudWatchEvents\CloudWatchEventsClient;
use Aws\Exception\AwsException;

/**
 * Test Event Pattern
 *
 * This code expects that you have AWS credentials set up per:
 * http://docs.aws.amazon.com/aws-sdk-php/v3/guide/guide/credentials.html
 */

$client = CloudWatchEventsClient::factory([
    'profile' => 'default',
    'region' => 'us-west-2',
    'version' => '2015-10-07'
]);

$exampleEvent = '{
  "version": "0",
  "id": "6a7e8feb-b491-4cf7-a9f1-bf3703467718",
  "detail-type": "EC2 Instance State-change Notification",
  "source": "aws.ec2",
  "account": "111122223333",
  "time": "2015-12-22T18:43:48Z",
  "region": "us-east-1",
  "resources": [
    "arn:aws:ec2:us-east-1:123456789012:instance/i-12345678"
  ],
  "detail": {
    "instance-id": "i-12345678",
    "state": "terminated"
  }
}';

$exampleEventPattern = '{
  "source": [ "aws.ec2" ],
  "detail-type": [ "EC2 Instance State-change Notification" ],
  "detail": {
    "state": [ "terminated" ]
  }
}';

try {
    $result = $client->testEventPattern([
        'Event' => $exampleEvent,
        'EventPattern' => $exampleEventPattern
    ]);
    var_dump($result);
} catch (AwsException $e) {
    // output error message if fails
    error_log($e->getMessage());
}
