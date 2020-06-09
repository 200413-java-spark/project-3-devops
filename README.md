# Project 3 DevOps

## Configuration
#### .gitignore files & locations
Properties file: src/main/resources/app.properties
Input file: src/main/resources/input/Oil_and_Gas_Annual_Production__Beginning_2001.csv
Input file (test): src/test/resources/input/Oil_and_Gas_Annual_Production__Beginning_2001.csv
Input file (test, small): src/test/resources/input/Oil_and_Gas_Annual_Production__Beginning_2001.small.csv

## Run Main program
mvn clean compile
mvn exec:java -Dexec.args="input 10 1"

## Tests
#### Test properties loader:
    mvn clean test -Dtest=AppPropertiesTest
#### Test database connection (WORKS ONLY ON VPC):
    mvn clean test -Dtest=DatabaseUtilTest
#### Test file reader:
    mvn clean test -Dtest=InputFileReaderTest
