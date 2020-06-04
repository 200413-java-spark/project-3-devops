package devops.util;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;

@Ignore
public class DatabaseUtilTest {

    @Test
    public void testConnection() {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();
        assertNotNull(connection);
        DatabaseUtil.closeConnection(connection);
    }

}