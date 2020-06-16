package devops.kafka.producer;

import com.salesforce.kafka.test.junit5.SharedKafkaTestResource;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.RegisterExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@TestMethodOrder(OrderAnnotation.class)
public class P3ProducerTest {
  private static P3Producer testProducer;
  private static Logger logger = LogManager.getLogger(P3ProducerTest.class);

  @RegisterExtension
  public static final SharedKafkaTestResource testKafka = new SharedKafkaTestResource();

  /**
   * set properties to connect to test kafka server, then create producer
   */
  @BeforeAll
  public static void init() {
    try {
      // load properties
      String kafkaConfigFilepath =
          P3ProducerTest.class.getClassLoader().getResource("kafka.properties").getFile();
      logger.info("Resources path! " + kafkaConfigFilepath);

      Properties testProps = new Properties();
      testProps.load(new FileReader(kafkaConfigFilepath));

      // need to set properties to connect to test server, but defaultconfig builder is protected
      // method for some reason, so doing unsafe reflection stuff to override access
      Class[] params = null;
      Method buildDefault = testKafka.getKafkaTestUtils().getClass()
          .getDeclaredMethod("buildDefaultClientConfig", params);
      buildDefault.setAccessible(true);
      Map<String, Object> defaultTestConfig =
          (Map<String, Object>) buildDefault.invoke(testKafka.getKafkaTestUtils(), (Object[]) null);
      logger.info("Test kafka props: " + defaultTestConfig);

      // update props to connect to test kafka
      defaultTestConfig.put("max.in.flight.requests.per.connection", "1");
      defaultTestConfig.put("retries", "5");
      defaultTestConfig.put("client.id", P3ProducerTest.class.getSimpleName() + " Producer");
      defaultTestConfig.put("batch.size", "0");
      for (Map.Entry<String, Object> prop : defaultTestConfig.entrySet()) {
        testProps.put(prop.getKey(), prop.getValue().toString());
      }
      logger.info("Updated props: " + testProps);

      // since p3producer class loads config file in constructor, we create a temp config file
      String testConfigFilepath = kafkaConfigFilepath.replace("kafka", "test");
      testProps.store(new FileWriter(new File(testConfigFilepath)), "test kafka props");

      // finally initiating producer to run on test kafka env
      testProducer = new P3Producer(testConfigFilepath);
    } catch (Exception e) {
      logger.error("error", e);
    }
  }

  @Test
  @Order(1)
  public void testInitialization() {
    assertNotNull(testKafka);
    assertNotNull(testProducer);
    logger.info("test kafka server and producer running...");
  }

  /**
   * test send() using Salesforce Kafka-JUnit5 extension
   */
  @Test
  @Order(2)
  public void producerAndConsumerTest() {
    try {
      logger.info("Using Kafka-JUnit5 to test...");

      // send messages to topic
      List<String> testMsgs = Arrays.asList("foo", "bar", "BLAH");
      testProducer.send("test", testMsgs);

      TopicDescription topicDescription = testKafka.getKafkaTestUtils().describeTopic("test");
      assertNotNull(topicDescription, "Should return a result");

      logger.info("Found topic with name '{}'", topicDescription.name());

      // verifying messages sent with test consumer
      List<ConsumerRecord<String, String>> recsReceived = testKafka.getKafkaTestUtils()
          .consumeAllRecordsFromTopic("test", StringDeserializer.class, StringDeserializer.class);
      assertNotNull(recsReceived, "Should have non-null results");
      logger.info("Found " + recsReceived.size() + " records");

      for (ConsumerRecord<String, String> rec : recsReceived) {
        logger.info("Found Key: {} with Value: {}", rec.key(), rec.value());
      }
      List<String> msgsReceived =
          recsReceived.stream().map(rec -> rec.value()).collect(Collectors.toList());

      assertEquals(testMsgs, msgsReceived);
    } catch (Exception e) {
      logger.error("Error at Kafka-JUnit!", e);
    }
  }

  /**
   * test send() by injecting Kafka's built-in mockProducer (needs to run last due to mutating test
   * producer object)
   */
  @Test
  @Order(3)
  public void mockProducerTest() {
    try {
      logger.info("Using MockProducer to test...");

      // replacing KafkaProducer with MockProducer to get history() method prior to sending
      testProducer.producer =
          new MockProducer<String, String>(true, new StringSerializer(), new StringSerializer());

      List<String> testMsgs = Arrays.asList("foo", "bar", "BLAH");
      testProducer.send("test", testMsgs);

      // verifying messages sent with MockProducer.history()
      List<ProducerRecord<String, String>> history =
          ((MockProducer<String, String>) testProducer.producer).history();

      assertNotNull(history, "Should have produced records");
      logger.info("Checking producer history...");
      for (ProducerRecord<String, String> item : history) {
        logger.info("Sent Value: {} to Topic {}", item.value(), item.topic());
      }

      List<ProducerRecord<String, String>> expected =
          Arrays.asList(new ProducerRecord<String, String>("test", "foo"),
              new ProducerRecord<String, String>("test", "bar"),
              new ProducerRecord<String, String>("test", "BLAH"));

      assertEquals(history, expected);
    } catch (Exception e) {
      logger.error("Error at MockProducer!", e);
    }
  }
}
