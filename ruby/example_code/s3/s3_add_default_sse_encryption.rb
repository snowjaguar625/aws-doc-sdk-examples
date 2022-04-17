# Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
#
# This file is licensed under the Apache License, Version 2.0 (the "License").
# You may not use this file except in compliance with the License. A copy of the
# License is located at
#
# http://aws.amazon.com/apache2.0/
#
# This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
# OF ANY KIND, either express or implied. See the License for the specific
# language governing permissions and limitations under the License.

require 'aws-sdk-s3' # In v2: require 'aws-sdk'

# Create S3 client
client = Aws::S3::Client.new(region: 'us-west-2')

# Set default encryption on bucket
client.put_bucket_encryption(
  bucket: 'my_bucket',
  server_side_encryption_configuration: {
    rules: [{
      apply_server_side_encryption_by_default: {
        sse_algorithm: 'AES256'
      }
    }]
  }
)
