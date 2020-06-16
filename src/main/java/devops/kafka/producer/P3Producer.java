package devops.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Basic producer to send messages to a Kafka topic
 */
public class P3Producer {
  private Properties kafkaProps = new Properties();
  public Producer<String, String> producer;

  public P3Producer(String configFilePath) throws IOException {
    kafkaProps.load(new FileInputStream(configFilePath));
    producer = new KafkaProducer<String, String>(kafkaProps);
  }

  /**
   * Send a list of messages to a topic
   */
  public void send(String topic, List<String> msgs) {
    for (String msg : msgs) {
      System.out.println("Sending message '" + msg + "' to topic '" + topic + "' at " + new Date());
      ProducerRecord<String, String> rec = new ProducerRecord<String, String>(topic, msg);
      producer.send(rec);
    }
    producer.flush();
  }

  public void close() {
    producer.close();
  }
}
