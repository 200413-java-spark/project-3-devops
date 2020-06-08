package devops.kafka.producer;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class InputFileReaderTest {
    
    public static final String inputFile = "src/test/resources/input/Oil_and_Gas_Annual_Production__Beginning_2001.small.csv";
    public static final String inputFileChronological = "src/test/resources/input/Oil_and_Gas_Annual_Production__Beginning_2001.chronological.small.csv";
    public static final String inputFileChronologicalSample = "src/test/resources/input/Oil_and_Gas_Annual_Production__Beginning_2001.chronological.very-small.csv";
    public static final String outputFile = "src/test/resources/output/data.small.csv";
    public static final String outputFileChronological = "src/test/resources/output/data.chronological.small.csv";
    
    @Test
    public void writeToFileTest() {
        InputFileReader fileReader = new InputFileReader();
        fileReader.execute(inputFileChronologicalSample, outputFileChronological, 3, 1);
    }

}