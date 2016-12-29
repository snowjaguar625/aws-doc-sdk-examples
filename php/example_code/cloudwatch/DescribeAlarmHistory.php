<?php
/**
 * Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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
use Aws\CloudWatch\CloudWatchClient;
use Aws\Exception\AwsException;

/**
 * Describe Alarm History in CloudWatch
 *
 * This code expects that you have AWS credentials set up per:
 * http://docs.aws.amazon.com/aws-sdk-php/v3/guide/guide/credentials.html
 */

$ALARM_NAME = "<ALARM_NAME>";

$client = CloudWatchClient::factory([
        'profile' => 'default',
        'region'  => 'us-west-2',
        'version' => '2010-08-01'
]);

try {
    $result = $client->describeAlarmHistory([
        'AlarmName' => $ALARM_NAME
    ]);
    echo "Alarm History for ".$ALARM_NAME.":\n";
    foreach ($result['AlarmHistoryItems'] as $alarm){
        echo $alarm['Timestamp']." ".$alarm['HistoryItemType']."\n";
    }
}catch (AwsException $e) {
    // output error message if fails
    echo $e->getMessage();
    echo "\n";
}
