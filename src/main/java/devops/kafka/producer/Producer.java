package devops.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Producer {
  private Properties kafkaProps = new Properties();
  private KafkaProducer<String, String> producer;

  public Producer(String configFilePath) throws IOException {
    kafkaProps.load(new FileInputStream(configFilePath));
    producer = new KafkaProducer<String, String>(kafkaProps);
  }

  public void send(String topic, List<String> msgs) {
    for (String msg : msgs) {
      System.out.println("Sending message to topic '" + topic + "' at " + new Date());
      ProducerRecord<String, String> rec =
          new ProducerRecord<String, String>(topic, msg);
      producer.send(rec);
    }
    producer.flush();
  }

  public void close() {
    producer.close();
  }

  /*// Simple placeholder class for topic and message.
  public static class KafkaMessage {
    private String topic;
    private String message;

    public KafkaMessage(String topic, String message) {
      this.topic = topic;
      this.message = message;
    }
  }*/
}
