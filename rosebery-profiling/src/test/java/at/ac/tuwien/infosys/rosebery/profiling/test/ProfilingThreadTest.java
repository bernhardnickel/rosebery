package at.ac.tuwien.infosys.rosebery.profiling.test;

import at.ac.tuwien.infosys.rosebery.profiling.ProfilingRunnable;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class ProfilingThreadTest {

    @Test
    public void test() throws Exception {
        ProfilingRunnable runnable = new ProfilingRunnable();
        runnable.setInterval(100l);
        runnable.setThreadId(Thread.currentThread().getId());

        new Thread(runnable).start();


        List<Object> l = new ArrayList<>();
        for(int i = 0; i < 1000; i++) {
            l.add(new Byte[1000]);
            Thread.sleep(5l);
        }
        l.clear();

        Thread.sleep(1000);

        runnable.interrupt();


        System.out.println(runnable.getResult());
    }
}
