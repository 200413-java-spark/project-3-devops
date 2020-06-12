package devops;

import devops.data.Parser;
import devops.kafka.producer.Producer;

import java.util.ArrayList;


public class Streamer {
    public static void main(String[] args) throws Exception {
        // Provide a source file and specify number of lines to stream a second
        String dataFilePath = args[0];
        int queueSize = Integer.parseInt(args[1]);
        String configFilePath = "src/main/resources/kafka.properties";

        // Kafka producer.
        Producer kafkaProducer = new Producer(configFilePath);

        // Data parser.
        Parser parser = new Parser(dataFilePath);

        // Read our csv input data, extract topic, build Kafka message and push to queue.
        ArrayList<String> lines;

        while (!(lines = parser.read(queueSize)).isEmpty()) {
            kafkaProducer.send("oil", lines);

            // Sleep one second to simulate time delays between incoming of real quotes.
            Thread.sleep(1000);
        }

        // Close open streams.
        kafkaProducer.close();
        parser.close();
    }
}
