package at.ac.tuwien.infosys.rosbery.jvm.profiling;

import at.ac.tuwien.infosys.rosebery.common.factory.node.NodeFactory;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.JvmProfile;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;
import java.util.Map;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class ProfilingThread implements Runnable {
    private static final String JVM_PROFILING_INTERVAL_SYSTEM_PROPERTY="rosebery.JvmProfilingInterval";
    private static final String JVM_PROFILING_POLLING_INTERVAL_SYSTEM_PROPERTY="rosebery.JvmProfilingPollingInterval";

    private static final ProfilingThread instance = new ProfilingThread();

    private OperatingSystemMXBean osMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    private MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    private PollingRunnable pollingRunnable = null;

    private long lastProcessCPUTime = Long.MIN_VALUE;

    private long interval;

    public ProfilingThread() {
        interval = Long.valueOf(System.getProperty(JVM_PROFILING_INTERVAL_SYSTEM_PROPERTY));
        long pollingInterval = Long.valueOf(System.getProperty(JVM_PROFILING_POLLING_INTERVAL_SYSTEM_PROPERTY));

        pollingRunnable = new PollingRunnable(pollingInterval);
        Thread pollingThread = new Thread(pollingRunnable);
        pollingThread.setDaemon(true);
        pollingThread.start();

        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void run() {
        lastProcessCPUTime = osMXBean.getProcessCpuTime();

        while (true) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {}

            JvmProfile profile = snapshot();

            lastProcessCPUTime = osMXBean.getProcessCpuTime();

            PublicationService.getPublicationService().publish(profile);
        }
    }

    private JvmProfile snapshot() {
        JvmProfile profile = new JvmProfile();



        profile.setNode(NodeFactory.getNodeFactory().getNode());
        profile.setNanoTime(System.nanoTime());

        profile.setProcessCpuTime(osMXBean.getProcessCpuTime() - lastProcessCPUTime);
        profile.setHeapMax(memoryMXBean.getHeapMemoryUsage().getMax());

        Map<PollingRunnable.ResultKey, Map<PollingRunnable.Stats, Double>> stats = pollingRunnable.getStats();

        profile.setHeapUsageMax(stats.get(PollingRunnable.ResultKey.HEAP_USAGE).get(PollingRunnable.Stats.MAX));
        profile.setHeapUsageMin(stats.get(PollingRunnable.ResultKey.HEAP_USAGE).get(PollingRunnable.Stats.MIN));
        profile.setHeapUsageAvg(stats.get(PollingRunnable.ResultKey.HEAP_USAGE).get(PollingRunnable.Stats.AVG));

        profile.setSystemCpuLoadMax(stats.get(PollingRunnable.ResultKey.SYSTEM_CPU_LOAD).get(PollingRunnable.Stats.MAX));
        profile.setSystemCpuLoadMin(stats.get(PollingRunnable.ResultKey.SYSTEM_CPU_LOAD).get(PollingRunnable.Stats.MIN));
        profile.setSystemCpuLoadAvg(stats.get(PollingRunnable.ResultKey.SYSTEM_CPU_LOAD).get(PollingRunnable.Stats.AVG));

        profile.setProcessCpuLoadMax(stats.get(PollingRunnable.ResultKey.PROCESS_CPU_LOAD).get(PollingRunnable.Stats.MAX));
        profile.setProcessCpuLoadMin(stats.get(PollingRunnable.ResultKey.PROCESS_CPU_LOAD).get(PollingRunnable.Stats.MIN));
        profile.setProcessCpuLoadAvg(stats.get(PollingRunnable.ResultKey.PROCESS_CPU_LOAD).get(PollingRunnable.Stats.AVG));

        return profile;
    }
}
