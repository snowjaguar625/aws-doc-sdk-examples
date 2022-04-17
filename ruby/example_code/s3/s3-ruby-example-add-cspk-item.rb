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
key_file = 'public_key.pem'

# Get file content as string
file = File.open(item, "rb")
contents = file.read
file.close

public_key = IO.read(key_file)

key = OpenSSL::PKey::RSA.new(public_key)

begin
  # encryption client
  enc_client = Aws::S3::Encryption::Client.new(encryption_key: key)

  # Add encrypted item to bucket
  resp = enc_client.put_object({
    body: contents,
    bucket: bucket,
    key: item_name,
  })

  puts 'Added ' + item_name + ' to bucket ' + bucket + ' using key from ' + key_file
rescue StandardError => err
  puts 'Could not add item'
  puts 'Error:'
  puts ex.message
end
