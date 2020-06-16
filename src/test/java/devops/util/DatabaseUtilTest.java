package devops.util;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;

@Disabled
public class DatabaseUtilTest {

    @Test
    public void testConnection() {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();
        assertNotNull(connection);
        DatabaseUtil.closeConnection(connection);
    }

}