package at.ac.tuwien.infosys.rosebery.jvm.profiling;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class ProfilingTest {
    @BeforeClass
    public static void beforeClass() {
        System.setProperty("rosebery.JvmProfilingInterval", "100");
        System.setProperty("rosebery.JvmProfilingPollingInterval", "10");
    }

    @Test
    public void test() throws Exception {
        Class.forName("at.ac.tuwien.infosys.rosebery.jvm.profiling.ProfilingThread");
        Thread.sleep(3000);
    }
}
