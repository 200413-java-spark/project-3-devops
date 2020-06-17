package devops.data;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ParserTest {
  private static Logger logger = LogManager.getLogger(ParserTest.class);
  private static Parser testParser;

  @BeforeAll
  public static void init() {
    try {
      String testFilePath = ParserTest.class.getClassLoader().getResource("test.csv").getFile();
      logger.info("Resources path! " + testFilePath);
      testParser = new Parser(testFilePath);
    } catch (Exception e) {
      logger.error("Error loading! ", e);
    }
  }

  @Test
  public void testRead() {
    try {
      ArrayList<String> linez = testParser.read(3);
      assertNotNull(linez);
      assertEquals(linez.size(), 3);
      logger.info("Converted! " + linez.stream().collect(Collectors.joining(", ")));

      Boolean trueDat = false;
      for (String line : linez) {
        if (line.contains("blahhhhhhhhh")) {
          trueDat = true;
        }
      }

      assertTrue(trueDat);
    } catch (Exception e) {
      logger.error("Error reading! ", e);
      fail(e.getMessage());
    }
  }
}
