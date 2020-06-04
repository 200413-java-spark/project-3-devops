package devops.util;

import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertFalse;

@Ignore
public class AppPropertiesTest {

    @Test
    public void loadPropertiesTest() {
        String user = null;
        user = AppProperties.PROPERTIES.getProperty("DATABASE_USER");
        assertFalse(user == null || user.isEmpty());
    }

}