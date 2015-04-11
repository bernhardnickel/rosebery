package at.ac.tuwien.infosys.rosebery.profiling.test;

import at.ac.tuwien.infosys.rosebery.profiling.ProfilingThread;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class ProfilingThreadTest {

    @Test
    public void test() throws Exception {
        ProfilingThread thread = new ProfilingThread();
        thread.setInterval(100l);
        thread.setThreadId(Thread.currentThread().getId());

        thread.start();


        List<Object> l = new ArrayList<>();
        for(int i = 0; i < 1000; i++) {
            l.add(new Byte[1000]);
            Thread.sleep(5l);
        }
        l.clear();

        Thread.sleep(1000);

        thread.interrupt();


        System.out.println(thread.getResult());
    }
}
