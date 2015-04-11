package at.ac.tuwien.infosys.rosebery.profiling.test;

import at.ac.tuwien.infosys.rosebery.profiling.ProfilingConfiguration;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class ProfilingConfigurationTest {

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("rosebery.profilingConfig", "src/test/resources/roseberyProfiling.properties");
    }

    @Test
    public void test() {
        long interval = Long.valueOf(ProfilingConfiguration.getInstance().getInterval(new Object()));

        assertEquals(10l, interval);

    }
}
