package devops;

import devops.kafka.producer.InputFileReader;

public class App 
{
    public static void main(String[] args)
    {
        String option = args[0];

        if (option.equals("input")) {
            int lineLimit = Integer.parseInt(args[1]);
            int timeInterval = Integer.parseInt(args[2]) ;
            String inputFile = "src/test/resources/input/Oil_and_Gas_Annual_Production__Beginning_2001.chronological.very-small.csv";
            String outputFile = "data/data.chronological.small.csv";
            InputFileReader fileReader = new InputFileReader();
            fileReader.execute(inputFile, outputFile, lineLimit, timeInterval);
        }
    }
}