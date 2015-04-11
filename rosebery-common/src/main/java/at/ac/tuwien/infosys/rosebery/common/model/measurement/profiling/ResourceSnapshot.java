package at.ac.tuwien.infosys.rosebery.common.model.measurement.profiling;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class ResourceSnapshot implements Measurement, Comparable<ResourceSnapshot> {
    private long nanoTime;

    private double systemCpuLoad;
    private double processCpuLoad;

    private long processCpuTime;
    private long threadCpuTime;

    private long heapMax;
    private long heapUsage;
    private long nonHeapMax;
    private long nonHeapUsage;

    public long getNanoTime() {
        return nanoTime;
    }

    public void setNanoTime(long nanoTime) {
        this.nanoTime = nanoTime;
    }

    public double getSystemCpuLoad() {
        return systemCpuLoad;
    }

    public void setSystemCpuLoad(double systemCpuLoad) {
        this.systemCpuLoad = systemCpuLoad;
    }

    public double getProcessCpuLoad() {
        return processCpuLoad;
    }

    public void setProcessCpuLoad(double processCpuLoad) {
        this.processCpuLoad = processCpuLoad;
    }

    public long getProcessCpuTime() {
        return processCpuTime;
    }

    public void setProcessCpuTime(long processCpuTime) {
        this.processCpuTime = processCpuTime;
    }

    public long getThreadCpuTime() {
        return threadCpuTime;
    }

    public void setThreadCpuTime(long threadCpuTime) {
        this.threadCpuTime = threadCpuTime;
    }

    public long getHeapMax() {
        return heapMax;
    }

    public void setHeapMax(long heapMax) {
        this.heapMax = heapMax;
    }

    public long getHeapUsage() {
        return heapUsage;
    }

    public void setHeapUsage(long heapUsage) {
        this.heapUsage = heapUsage;
    }

    public long getNonHeapMax() {
        return nonHeapMax;
    }

    public void setNonHeapMax(long nonHeapMax) {
        this.nonHeapMax = nonHeapMax;
    }

    public long getNonHeapUsage() {
        return nonHeapUsage;
    }

    public void setNonHeapUsage(long nonHeapUsage) {
        this.nonHeapUsage = nonHeapUsage;
    }

    @Override
    public int compareTo(ResourceSnapshot o) {
        return Long.compare(nanoTime, o.nanoTime);
    }
}
