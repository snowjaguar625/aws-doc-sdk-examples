.. Copyright 2010-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.

   This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0
   International License (the "License"). You may not use this file except in compliance with the
   License. A copy of the License is located at http://creativecommons.org/licenses/by-nc-sa/4.0/.

   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   either express or implied. See the License for the specific language governing permissions and
   limitations under the License.

####################
aws-doc-sdk-examples
####################

This repository contains AWS SDK examples used in the public `AWS documentation repositories
<https://www.github.com/awsdocs>`_.

About the examples
==================

The SDK examples are organized by programming language. For instance, all of the examples for the
`AWS SDK for Java Developer Guide <https://www.github.com/awsdocs/aws-java-developer-guide>`_ are
kept in the `java <java>`_ directory.

Building and running examples
-----------------------------

Within each of the language-scoped directories, you'll find a **README** file that explains how to
build and run the examples contained within it.

The example code itself is present in the ``example_code`` subdirectory, and is organized further by
the AWS service abbreviation ("s3" for `Amazon S3 <https://aws.amazon.com/s3>`_ examples, and so on).


How examples are used in the documentation
==========================================

Examples are automatically included within each developer guide by ``build_docs.py`` (present in
each documentation project), which clones this repository and then copies files from the appropriate
``example_code`` directory into the ``doc_build`` directory prior to building.

Within a guide's source-files, examples are referenced using Sphinx's `literalinclude
<http://www.sphinx-doc.org/en/stable/markup/code.html#includes>`_ directive. For example::

   .. literalinclude:: example_code/s3/src/main/java/aws/example/s3/CreateBucket.java
      :lines: 43-50
      :dedent: 8


Submitting code for use in AWS documentation
============================================

If you plan to contribute examples for use in the documentation (the purpose of this repository),
please read this section carefully to ensure that your code submissions can be properly used in
documentation.

* **Make sure that the code can be built and run**. There's nothing more frustrating in developer
  documentation than code examples that don't work. Build the code and test it before submitting it!

* **Format code lines to 80 characters**. Long lines can be enclosed in a scrollable box for HTML,
  but in a PDF build, long lines will often spill off of the side of the page, making the code
  unreadable. If your code includes long text strings, consider breaking these into smaller chunks
  so that they can be concatenated together.

* **Use short(er) variable names**. To aid in readability and to help keep line length down, use
  *short, yet descriptive*, names for variables. Do *not* simply mimic class names when creating
  variables that represent an object of that class. It nearly always results in excessively long
  variable names, making it difficult to keep code lines within 80 characters.

* **Use spaces, not tabs, for indentation**. Tabs are variable-length in most editors, but will
  usually render as 8 characters wide in printed documentation. *Always use spaces* to ensure
  consistent formatting in edited + printed code.

  You can ignore this rule for makefiles, which may *require* the use of tabs, but these are
  typically only used for building examples, and are not included in documentation.

* **Minimize the use of comments**. Code is ignored for translation, and so any comments in code
  will not be translated for the printed documentation's target language. Comments should not be
  needed in most code used for documentation, since the goal is clarity and ease of understanding.
  By making code self-explanatory, you'll make better code for documentation and reduce the need to
  add comments.

* **Place comments on separate lines from code**. If you *must* add a comment for explanation or any
  other purpose, make sure that it is placed on a separate line from code (*not* inline). This
  allows readers of the source file to read the comment, yet it can be stripped out when including
  snippets from the file within documentation.

* **All code must be submitted under the Apache 2.0 license**, as noted in the following **Copyright
  and License** section.

Copyright and License
=====================

All content in this repository, unless otherwise stated, is Copyright © 2010-2017, Amazon Web
Services, Inc. or its affiliates. All rights reserved.

Except where otherwise noted, all examples in this collection are licensed under the `Apache
license, version 2.0 <http://www.apache.org/licenses/LICENSE-2.0>`_ (the "License"). The full
license text is provided in the ``LICENSE`` file accompanying this repository.

