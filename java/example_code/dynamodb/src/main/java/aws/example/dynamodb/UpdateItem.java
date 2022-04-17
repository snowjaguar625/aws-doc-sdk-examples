/*
   Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This file is licensed under the Apache License, Version 2.0 (the "License").
   You may not use this file except in compliance with the License. A copy of
   the License is located at

    http://aws.amazon.com/apache2.0/

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
   CONDITIONS OF ANY KIND, either express or implied. See the License for the
   specific language governing permissions and limitations under the License.
*/
package aws.example.dynamodb;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.AmazonServiceException;
import java.util.HashMap;

/**
 * Update a DynamoDB table (change provisioned throughput).
 *
 * This code expects that you have AWS credentials set up per:
 * http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html
 */
public class UpdateItem
{
    public static void main(String[] args)
    {
        final String USAGE = "\n" +
            "To run this example, type a key name and an updated greeting.\n" +
            "If either the greeting or name contains spaces, surround the\n" +
            "value with quotes.\n\n" +
            "Ex:   UpdateItem World Hello\n" +
            "      UpdateItem Friend \"Good morning\"\n\n" +
            "Note: If the specified key doesn't exist, an error will result.\n";

        if (args.length < 2) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String table_name = "HelloTable";
        String name = args[0];
        String greeting = args[1];

        System.out.format("Updating item in %s\n", table_name);
        System.out.format("  Name    : %s\n", name);
        System.out.format("  Greeting: %s\n", greeting);

        HashMap<String,AttributeValue> item_key = new HashMap<String,AttributeValue>();
        item_key.put("Name", new AttributeValue(name));

        HashMap<String,AttributeValueUpdate> updated_values =
            new HashMap<String,AttributeValueUpdate>();

        updated_values.put("Greeting",
                new AttributeValueUpdate().withValue(new AttributeValue(greeting)));

        final AmazonDynamoDBClient ddb = new AmazonDynamoDBClient();

        try {
            ddb.updateItem(table_name, item_key, updated_values);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        System.out.println("Done!");
    }
}

