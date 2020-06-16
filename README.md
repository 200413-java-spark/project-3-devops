# Project 3 DevOps

## Quick n Dirty Way to Stream from File with Java

```bash
> mvn clean package
> java -jar target/project-3-devops-0.0.2-jar-with-dependencies.jar OaGAP3.csv 50
```

where `arg0` = path to csv file from which stream is to be simulated, `arg1` = number of messages to be sent to Producer per second

Only reads from the beginning of the provided file for now and writes to topic "oil".. but at least parses multi-line values within quotes correctly e.g. "New Georeferenced Column":"Ellington, NY\n(42.215868, -79.108075)"

Double-check "bootstrap.servers" value in `src/main/resources/kafka.properties`


## Configuration
#### .gitignore files & locations:
Properties file: src/main/resources/app.properties
Input file: src/main/resources/input/Oil_and_Gas_Annual_Production__Beginning_2001.csv
Input file (chronological): src/main/resources/input/Oil_and_Gas_Annual_Production__Beginning_2001.chronological.csv
Test input file: src/test/resources/input/Oil_and_Gas_Annual_Production__Beginning_2001.csv
Test input file (chronological): src/test/resources/input/Oil_and_Gas_Annual_Production__Beginning_2001.chronological.csv
Test input file (small): src/test/resources/input/Oil_and_Gas_Annual_Production__Beginning_2001.small.csv
Test input file (chronological, small): src/test/resources/input/Oil_and_Gas_Annual_Production__Beginning_2001.chronological.small.csv
Test input file (chronological, very small): src/test/resources/input/Oil_and_Gas_Annual_Production__Beginning_2001.chronological.very-small.csv

## Run Main program
#### Example:
    mvn clean compile
    mvn exec:java -Dexec.args="input 10 1"

## Tests
#### Test properties loader:
    mvn clean test -Dtest=AppPropertiesTest
#### Test database connection (WORKS ONLY ON VPC):
    mvn clean test -Dtest=DatabaseUtilTest
#### Test file reader:
    mvn clean test -Dtest=InputFileReaderTest
```
