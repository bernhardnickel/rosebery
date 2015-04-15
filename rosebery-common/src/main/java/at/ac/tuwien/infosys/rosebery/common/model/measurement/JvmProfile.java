package at.ac.tuwien.infosys.rosebery.common.model.measurement;

import at.ac.tuwien.infosys.rosebery.common.model.Node;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class JvmProfile implements Measurement {
    private long nanoTime;
    private Node node;

    private double systemCpuLoadMax, systemCpuLoadMin, systemCpuLoadAvg;
    private double processCpuLoadMax, processCpuLoadMin, processCpuLoadAvg;

    private long processCpuTime;

    private long heapMax;
    private double heapUsageMax, heapUsageMin, heapUsageAvg;

    public long getNanoTime() {
        return nanoTime;
    }

    public void setNanoTime(long nanoTime) {
        this.nanoTime = nanoTime;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public long getProcessCpuTime() {
        return processCpuTime;
    }

    public void setProcessCpuTime(long processCpuTime) {
        this.processCpuTime = processCpuTime;
    }

    public long getHeapMax() {
        return heapMax;
    }

    public void setHeapMax(long heapMax) {
        this.heapMax = heapMax;
    }

    public double getSystemCpuLoadMax() {
        return systemCpuLoadMax;
    }

    public void setSystemCpuLoadMax(double systemCpuLoadMax) {
        this.systemCpuLoadMax = systemCpuLoadMax;
    }

    public double getSystemCpuLoadMin() {
        return systemCpuLoadMin;
    }

    public void setSystemCpuLoadMin(double systemCpuLoadMin) {
        this.systemCpuLoadMin = systemCpuLoadMin;
    }

    public double getSystemCpuLoadAvg() {
        return systemCpuLoadAvg;
    }

    public void setSystemCpuLoadAvg(double systemCpuLoadAvg) {
        this.systemCpuLoadAvg = systemCpuLoadAvg;
    }

    public double getProcessCpuLoadMax() {
        return processCpuLoadMax;
    }

    public void setProcessCpuLoadMax(double processCpuLoadMax) {
        this.processCpuLoadMax = processCpuLoadMax;
    }

    public double getProcessCpuLoadMin() {
        return processCpuLoadMin;
    }

    public void setProcessCpuLoadMin(double processCpuLoadMin) {
        this.processCpuLoadMin = processCpuLoadMin;
    }

    public double getProcessCpuLoadAvg() {
        return processCpuLoadAvg;
    }

    public void setProcessCpuLoadAvg(double processCpuLoadAvg) {
        this.processCpuLoadAvg = processCpuLoadAvg;
    }

    public double getHeapUsageMax() {
        return heapUsageMax;
    }

    public void setHeapUsageMax(double heapUsageMax) {
        this.heapUsageMax = heapUsageMax;
    }

    public double getHeapUsageMin() {
        return heapUsageMin;
    }

    public void setHeapUsageMin(double heapUsageMin) {
        this.heapUsageMin = heapUsageMin;
    }

    public double getHeapUsageAvg() {
        return heapUsageAvg;
    }

    public void setHeapUsageAvg(double heapUsageAvg) {
        this.heapUsageAvg = heapUsageAvg;
    }
}
