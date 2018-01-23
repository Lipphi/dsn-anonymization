# DSN Anonymization tool

Compiling
---------

You need to have Maven installed (http://maven.apache.org). Once installed,
simply run:

    mvn clean install

Running
-------

To anonymize a DSN, launch the followfing command and it will output the anonymed version:
    java jar ./target/dsn-anonymization-1.0-SNAPSHOT.jar anonymize -in test/example.txt