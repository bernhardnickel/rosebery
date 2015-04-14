package at.ac.tuwien.infosys.rosebery.profiling;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.profiling.ResourceSnapshot;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class ProfilingRunnable implements Runnable {
    private long interval;
    private long threadId;

    private boolean interrupted = false;

    private long lastProcessCPUTime = Long.MIN_VALUE;
    private long lastThreadCPUTime = Long.MIN_VALUE;

    private OperatingSystemMXBean osMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    private ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    private MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    Set<ResourceSnapshot> snapshots = new TreeSet<>();

    @Override
    public void run() {
        lastProcessCPUTime = osMXBean.getProcessCpuTime();
        lastThreadCPUTime = threadMXBean.getThreadCpuTime(threadId);

        while (!interrupted) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
            }

            snapshot();

            lastProcessCPUTime = osMXBean.getProcessCpuTime();
            lastThreadCPUTime = threadMXBean.getThreadCpuTime(threadId);
        }

    }

    private void snapshot() {
        ResourceSnapshot rs = new ResourceSnapshot();

        rs.setProcessCpuTime(osMXBean.getProcessCpuTime() - lastProcessCPUTime);
        rs.setThreadCpuTime(threadMXBean.getThreadCpuTime(threadId) - lastThreadCPUTime);

        rs.setSystemCpuLoad(osMXBean.getSystemCpuLoad());
        rs.setProcessCpuLoad(osMXBean.getProcessCpuLoad());
        rs.setHeapMax(memoryMXBean.getHeapMemoryUsage().getMax());
        rs.setHeapUsage(memoryMXBean.getHeapMemoryUsage().getUsed());

        rs.setNanoTime(System.nanoTime());
        snapshots.add(rs);
    }

    public void interrupt() {
        interrupted = true;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public Set<ResourceSnapshot> getResult() {
        return snapshots;
    }
}
