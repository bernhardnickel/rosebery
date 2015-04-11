package at.ac.tuwien.infosys.rosebery.common.model.measurement.profiling;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;

import java.io.Serializable;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class ResourceSnapshot implements Serializable, Comparable<ResourceSnapshot> {
    private long nanoTime;

    private double systemCpuLoad;
    private double processCpuLoad;

    private long processCpuTime;
    private long threadCpuTime;

    private long heapMax;
    private long heapUsage;

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

    @Override
    public int compareTo(ResourceSnapshot o) {
        return Long.compare(nanoTime, o.nanoTime);
    }

    @Override
    public String toString() {
        return "ResourceSnapshot[" + nanoTime + "," + systemCpuLoad + "," + processCpuLoad + "," + processCpuTime + "," + threadCpuTime + "," + heapMax + "," + heapUsage + "]";
    }
}
