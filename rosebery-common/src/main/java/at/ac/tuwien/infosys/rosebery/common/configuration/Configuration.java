package at.ac.tuwien.infosys.rosebery.common.configuration;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class Configuration {

    private static final String FILE_NAME = "rosebery.properties";

    private static Properties properties = null;

    public static String getProperty(String key) {

        if (properties == null) {
            synchronized (properties) {
                if (properties == null) {
                    properties = new Properties();

                    try {
                        properties.load(ClassLoader.getSystemResourceAsStream(FILE_NAME));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        String value = (String)properties.get(key);

        if (value == null) {
            return System.getProperty(key);
        }

        return value;
    }
}
