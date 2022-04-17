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

    func main() {
        svc := ec2.New(session.New())
        opts := &ec2.CreateImageInput{
            Description: aws.String("image description"),
            InstanceId:  aws.String("i-abcdef12"),
            Name:        aws.String("image name"),
            BlockDeviceMappings: []*ec2.BlockDeviceMapping{
                &ec2.BlockDeviceMapping{
                    DeviceName: aws.String("/dev/sda1"),
                    NoDevice:    aws.String(""),
                },
                &ec2.BlockDeviceMapping{
                    DeviceName: aws.String("/dev/sdb"),
                    NoDevice:    aws.String(""),
                },
                &ec2.BlockDeviceMapping{
                    DeviceName: aws.String("/dev/sdc"),
                    NoDevice:    aws.String(""),
                },
            },
        }
        resp, err := svc.CreateImage(opts)
        if err != nil {
            fmt.Println(err)
            return
        }

        fmt.Println("success", resp)
    }
