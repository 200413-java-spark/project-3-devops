package devops.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class AppProperties {

    public static final Properties PROPERTIES = loadProperties();

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            FileInputStream props = new FileInputStream("src/main/resources/app.properties");
            properties.load(props);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}