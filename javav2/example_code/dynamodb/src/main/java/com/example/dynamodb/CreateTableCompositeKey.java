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
package com.example.dynamodb;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.CreateTableResponse;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;

import software.amazon.awssdk.services.dynamodb.DynamoDBClient;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;

/**
 * Create a DynamoDB table.
 *
 * Takes the name of the table to create. The table will contain a single
 * primary key, "Name".
 *
 * This code expects that you have AWS credentials set up per:
 * http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html
 */
public class CreateTableCompositeKey
{
    public static void main(String[] args)
    {
        final String USAGE = "\n" +
            "Usage:\n" +
            "    CreateTable <table>\n\n" +
            "Where:\n" +
            "    table - the table to create.\n\n" +
            "Example:\n" +
            "    CreateTable GreetingsTable\n";

        if (args.length < 1) {
            System.out.println(USAGE);
            System.exit(1);
        }

        /* Read the name from command args */
        String table_name = args[0];

        System.out.format("Creating table %s\n with a composite primary key:\n", table_name);
        System.out.format("* Language - partition key\n");
        System.out.format("* Greeting - sort key\n");

        CreateTableRequest request = CreateTableRequest.builder()
            .attributeDefinitions(
                  AttributeDefinition.builder()
                  .attributeName("Language")
                  .attributeType(ScalarAttributeType.S)
                  .build(),
                  AttributeDefinition.builder()
                  .attributeName("Greeting")
                  .attributeType(ScalarAttributeType.S)
                  .build())
            .keySchema(
            		KeySchemaElement.builder()
            		.attributeName("Language")
            		.keyType(KeyType.HASH)
            		.build(),
            		KeySchemaElement.builder()
            		.attributeName("Greeting")
            		.keyType(KeyType.RANGE)
            		.build())
            .provisionedThroughput(
            		ProvisionedThroughput.builder()
            		.readCapacityUnits(new Long(10))
            		.writeCapacityUnits(new Long(10)).build())
            .tableName(table_name)
            .build();

        DynamoDBClient ddb = DynamoDBClient.create();

        try {
            CreateTableResponse result = ddb.createTable(request);
        } catch (S3Exception e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        System.out.println("Done!");
    }
}

