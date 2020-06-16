package devops.util;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Disabled
public class AppPropertiesTest {

    @Test
    public void loadPropertiesTest() {
        String user = null;
        user = AppProperties.PROPERTIES.getProperty("DATABASE_USER");
        assertFalse(user == null || user.isEmpty());
    }

}