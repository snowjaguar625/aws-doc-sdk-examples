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

require 'aws-sdk-s3'
require 'openssl'

bucket = 'my_bucket'
item = 'my_item'
key_file = 'private_key.pem'
passphrase = 'Mary had a little lamb'

begin
  private_key = File.binread(key_file)
  key = OpenSSL::PKey::RSA.new(private_key, passphrase)

  # encryption client
  enc_client = Aws::S3::Encryption::Client.new(encryption_key: key)

  resp = enc_client.get_object(bucket: bucket, key: item)

  puts resp.body.read
rescue StandardError => ex
  puts 'Could not get item'
  puts 'Error message:'
  puts ex.message
end
