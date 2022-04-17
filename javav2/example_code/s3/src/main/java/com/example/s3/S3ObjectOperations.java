/*
 * Copyright 2011-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *    http://aws.amazon.com/apache2.0
 *
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.s3;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.Random;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CompletedMultipartUpload;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.sync.RequestBody;
import software.amazon.awssdk.sync.StreamingResponseHandler;

public class S3ObjectOperations {

    private static S3Client s3;

    public static void main(String[] args) throws IOException {
        Region region = Region.US_WEST_2;
        s3 = S3Client.builder().region(region).build();

        String bucket = "bucket" + System.currentTimeMillis();
        String key = "key";

        createBucket(bucket, region);

        // Put Object
        s3.putObject(PutObjectRequest.builder().bucket(bucket).key(key)
                                     .build(),
                     RequestBody.of(getRandomByteBuffer(10_000)));


        // Multipart Upload a file
        String multipartKey = "multiPartKey";
        multipartUpload(bucket, multipartKey);


        // Get Object
        s3.getObject(GetObjectRequest.builder().bucket(bucket).key(key).build(),
                     StreamingResponseHandler.toFile(Paths.get("myfile.out")));


        // Delete Object
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucket).key(key).build();
        s3.deleteObject(deleteObjectRequest);

        // Delete Object
        deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucket).key(multipartKey).build();
        s3.deleteObject(deleteObjectRequest);


        deleteBucket(bucket);
    }


    private static void createBucket(String bucket, Region region) {
        s3.createBucket(CreateBucketRequest
                                .builder()
                                .bucket(bucket)
                                .createBucketConfiguration(
                                        CreateBucketConfiguration.builder()
                                                                 .locationConstraint(region.value())
                                                                 .build())
                                .build());
    }

    private static void deleteBucket(String bucket) {
        DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder().bucket(bucket).build();
        s3.deleteBucket(deleteBucketRequest);
    }

    /**
     * Uploading an object to S3 in parts
     */
    private static void multipartUpload(String bucketName, String key) throws IOException {

        int MB = 1024 * 1024;
        // First create a multipart upload and get upload id
        CreateMultipartUploadRequest createMultipartUploadRequest = CreateMultipartUploadRequest.builder()
                                                                                                .bucket(bucketName).key(key)
                                                                                                .build();
        CreateMultipartUploadResponse response = s3.createMultipartUpload(createMultipartUploadRequest);
        String uploadId = response.uploadId();
        System.out.println(uploadId);

        // Upload all the different parts of the object
        UploadPartRequest uploadPartRequest1 = UploadPartRequest.builder().bucket(bucketName).key(key)
                                                                .uploadId(uploadId)
                                                                .partNumber(1).build();
        String etag1 = s3.uploadPart(uploadPartRequest1, RequestBody.of(getRandomByteBuffer(5 * MB))).eTag();
        CompletedPart part1 = CompletedPart.builder().partNumber(1).eTag(etag1).build();

        UploadPartRequest uploadPartRequest2 = UploadPartRequest.builder().bucket(bucketName).key(key)
                                                                .uploadId(uploadId)
                                                                .partNumber(2).build();
        String etag2 = s3.uploadPart(uploadPartRequest2, RequestBody.of(getRandomByteBuffer(3 * MB))).eTag();
        CompletedPart part2 = CompletedPart.builder().partNumber(2).eTag(etag2).build();


        // Finally call completeMultipartUpload operation to tell S3 to merge all uploaded
        // parts and finish the multipart operation.
        CompletedMultipartUpload completedMultipartUpload = CompletedMultipartUpload.builder().parts(part1, part2).build();
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                CompleteMultipartUploadRequest.builder().bucket(bucketName).key(key).uploadId(uploadId)
                                              .multipartUpload(completedMultipartUpload).build();
        s3.completeMultipartUpload(completeMultipartUploadRequest);
    }

    private static ByteBuffer getRandomByteBuffer(int size) throws IOException {
        byte[] b = new byte[size];
        new Random().nextBytes(b);
        return ByteBuffer.wrap(b);
    }
}
