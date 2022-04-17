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

// Load the AWS SDK for Node.js
var AWS = require('aws-sdk');
// Set the region 
AWS.config.update({region: 'REGION'});

// Create createReceiptRule params 
var params = {
 Rule: {
  Actions: [
     {
    S3Action: {
     BucketName: "S3_BUCKET_NAME/*",
     ObjectKeyPrefix: "email"
    }
   }
  ],
    Recipients: [
      'DOMAIN | EMAIL_ADDRESS',
      /* more items */
    ],
  Enabled: true | false,
  Name: "RULE_NAME",
  ScanEnabled: true | false,
  TlsPolicy: "Optional"
 },
 RuleSetName: "RULE_SET_NAME"
};

// Create the promise and SES service object
var newRulePromise = new AWS.SES({apiVersion: '2010-12-01'}).createReceiptRule(params).promise();

// Handle promise's fulfilled/rejected states
newRulePromise.then(
  function(data) {
    console.log("Rule created");
  }).catch(
    function(err) {
    console.error(err, err.stack);
  });
