package devops;

import devops.data.Parser;
import devops.kafka.producer.Producer;

import java.util.ArrayList;


public class Streamer {
    public static void main(String[] args) throws Exception {
        String dataFilePath = args[0];
        String configFilePath = "src/main/resources/kafka.properties";

        // Kafka producer.
        Producer kafkaProducer = new Producer(configFilePath);

        // Data parser.
        Parser parser = new Parser(dataFilePath);

        // Read our csv input data, extract topic, build Kafka message and push to queue.
        ArrayList<String> lines;

        while (!(lines = parser.read(5)).isEmpty()) {
            kafkaProducer.send("oil", lines);

            // Sleep one second to simulate time delays between incoming of real quotes.
            Thread.sleep(1000);
        }

        // Close open streams.
        kafkaProducer.close();
        parser.close();
    }
}
