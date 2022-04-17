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

require '/Users/agshasha/IdeaProjects/aws-sdk-php/vendor/autoload.php';

use Aws\S3\S3Client;
use Aws\Exception\AwsException;

/**
 * Delete an Amazon S3 bucket.
 *
 * This code expects that you have AWS credentials set up per:
 * http://docs.aws.amazon.com/aws-sdk-php/v2/guide/credentials.html
 *
 * ++ Warning ++ This code will actually delete the bucket that you specify, as
 *               well as any objects within it!
 */

$BUCKET_NAME='<BUCKET-NAME>';

//Create a S3Client
$s3Client = new S3Client([
    'region' => 'us-west-2',
    'version' => '2006-03-01'
    ]);

//Delete all Objects when versioning is not enabled 
try {
    $objects = $s3Client->getIterator('ListObjects',([
        'Bucket' => $BUCKET_NAME
    ]));
    echo "Keys retrieved!\n";
    foreach ($objects as $object) {
        echo $object['Key'] . "\n";
        $result = $s3Client->deleteObject([
            'Bucket' => $BUCKET_NAME,
            'Key' => $object['Key'],
        ]);
    }
    $result = $s3Client->deleteBucket([
        'Bucket' => $BUCKET_NAME,
    ]);
} catch (S3Exception $e) {
    echo $e->getMessage() . "\n";
}

//Delete bucket and all versioned objects inside bucket when versioning is enabled.
try {
    $versions = $s3Client->listObjectVersions([
        'Bucket' => $BUCKET_NAME
        ])->getPath('Versions');
    echo "Keys retrieved!\n";
    foreach ($versions as $version) {
        echo $version['Key'] . "\n";
        echo $version['VersionId'] . "\n";
        $result = $s3Client->deleteObject([
            'Bucket' => $bucket,
            'Key' => $version['Key'],
            'VersionId' => $version['VersionId']
            ]);
    }
    $result = $s3Client->deleteBucket([
        'Bucket' => $BUCKET_NAME,
        ]);    
} catch (S3Exception $e) {
    echo $e->getMessage() . "\n";
}
