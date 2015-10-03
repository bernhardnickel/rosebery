package at.ac.tuwien.infosys.rosebery.profiling;

import at.ac.tuwien.infosys.rosebery.common.configuration.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Loads profiling configuration from a properties file
 *
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class ProfilingConfiguration {
    private static final String PROFILING_CONFIG_SYSTEM_PROPERTY = "rosebery.profilingConfig";

    private static final String INTERVAL_PROPERTY_POSTFIX = ".profilingInterval";

    private static ProfilingConfiguration instance;

    private Properties properties = new Properties();

    private ProfilingConfiguration() {
        try {
            properties.load(new FileInputStream(Configuration.getProperty(PROFILING_CONFIG_SYSTEM_PROPERTY)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ProfilingConfiguration getInstance() {
        if (instance == null) {
            synchronized (ProfilingConfiguration.class) {
                if (instance == null) {
                    instance = new ProfilingConfiguration();
                }
            }
        }
        return instance;
    }

    public long getInterval(Object jpo) {
        return Long.valueOf(properties.getProperty(jpo.getClass().getName() + INTERVAL_PROPERTY_POSTFIX));
    }
}
