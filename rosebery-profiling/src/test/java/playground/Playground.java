package playground;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class Playground {
    public static void main(String... args) {
        OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();

        System.out.println(os instanceof com.sun.management.OperatingSystemMXBean);
        System.out.println(os);


    }
}
