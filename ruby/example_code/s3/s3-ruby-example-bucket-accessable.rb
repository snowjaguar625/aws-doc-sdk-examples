# Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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

require 'aws-sdk'

s3 = Aws::S3::Resource.new(region: 'us-west-2')

# Does such a bucket exist?
found_bucket = s3.buckets.any? { |b| b.name == 'my-bucket' }

# If so, is it in this region?
if found_bucket
  found_bucket = s3.client.get_bucket_location(bucket: 'my-bucket').location_constraint == 'us-east-1'
end
