.. Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

#######################################
AWS SDK for Java Documentation Examples
#######################################

These are examples for the AWS SDK for Java public documentation.

Prerequsites
============

To build and run these examples, you'll need:

* Apache Maven (>3.0)
* AWS SDK for Java (downloaded and extracted somewhere on your machine)
* AWS credentials, either configured in a local AWS credentials file or by setting the
  AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY environment variables

Building the examples
=====================

The examples are divided into directories by AWS service (``s3``, ``dynamodb``, and so on). Within
each of these directories, you'll find a ``pom.xml`` file used for building the examples with Maven,
and a ``Makefile`` that wraps the Maven commands for those of you who also have ``make`` installed.

To build, open a command-line (terminal) window and change to the directory containing the examples
you want to build/run. Then type::

   mvn package

or, if you have ``make``, you can simply type::

   make

To begin the build process. Maven will download any dependencies (such as components of the AWS SDK
for Java) that it needs for building.

Once the examples have been built, you can run them to see them in action.

Running the examples
====================

.. important:: The examples perform AWS operations for the account and region for which you've
   specified credentials, and you may incur AWS service charges by running them. Please visit the
   `AWS Pricing <https://aws.amazon.com/pricing/>` page for details about the charges you can expect
   for a given service and operation.

   Some of these examples perform *destructive* operations on AWS resources, such as deleting an
   Amazon S3 bucket or an Amazon DynamoDB table. **Be very careful** when running an operation that
   may delete or modify AWS resources in your account. It's recommended that you create test
   resources for experimentation with these examples.

To run the examples, you'll need the AWS SDK for Java libraries in your CLASSPATH. You can set them
directly, such as::

    export CLASSPATH=/path/to/aws-java-sdk/lib/*:/path/to/aws-java-sdk/third-party/lib/*

Where ``/path/to/aws-java-sdk`` is the path to where you extracted the AWS Java SDK download (it
should contain the ``lib`` and ``third-party/lib`` directories).

You can then run it like this::

    java aws.example.s3.ListBuckets -cp target/s3examples-1.0.jar:$CLASSPATH

I've included a ``bash`` script that you can run on most systems that assumes you've set the path to
the Java SDK directory in the ``JAVA_SDK_HOME`` environment variable. For example::

    export JAVA_SDK_HOME=/path/to/aws-java-sdk

Once you've set the variable, you can execute ``run_example.sh`` as shown::

    ./run_example.sh ListBuckets

and it will run the ListBuckets example (assuming that you've built the examples first!).

