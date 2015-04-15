package at.ac.tuwien.infosys.rosbery.jvm.profiling;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.*;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class PollingRunnable implements Runnable {

    public enum ResultKey {
        HEAP_USAGE,
        SYSTEM_CPU_LOAD,
        PROCESS_CPU_LOAD
    }

    public enum Stats {
        MAX,
        MIN,
        AVG
    }

    private OperatingSystemMXBean osMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    private MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    List<Double> heapUsage = new ArrayList<>();
    List<Double> systemCpuLoad = new ArrayList<>();
    List<Double> processCpuLoad = new ArrayList<>();

    private long interval;

    public PollingRunnable(long interval) {
        this.interval = interval;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (PollingRunnable.class) {
                heapUsage.add(new Long(memoryMXBean.getHeapMemoryUsage().getUsed()).doubleValue());
                systemCpuLoad.add(osMXBean.getSystemCpuLoad());
                processCpuLoad.add(osMXBean.getProcessCpuLoad());

                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) { }
            }
        }
    }

    public Map<ResultKey, Map<Stats, Double>> getStats() {
        Map<ResultKey, Map<Stats, Double>> result = new HashMap<>();

        synchronized (PollingRunnable.class) {
            result.put(ResultKey.HEAP_USAGE, getStats(heapUsage));
            result.put(ResultKey.SYSTEM_CPU_LOAD, getStats(systemCpuLoad));
            result.put(ResultKey.PROCESS_CPU_LOAD, getStats(processCpuLoad));


            heapUsage.clear();
            systemCpuLoad.clear();
            processCpuLoad.clear();
        }

        return result;
    }

    private Map<Stats, Double> getStats(List<Double> values) {
        Map<Stats, Double> result = new HashMap<>();
        Collections.sort(values);

        result.put(Stats.MIN, values.get(0));
        result.put(Stats.MAX, values.get(values.size() - 1));

        double sum = 0;
        for (Double d : values) { sum += d; }

        result.put(Stats.AVG, sum / (double)values.size());

        return result;
    }
}
