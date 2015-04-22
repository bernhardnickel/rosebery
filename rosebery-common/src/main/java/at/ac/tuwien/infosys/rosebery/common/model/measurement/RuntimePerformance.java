package at.ac.tuwien.infosys.rosebery.common.model.measurement;

import at.ac.tuwien.infosys.rosebery.common.model.Node;

/**
 * Runtime performance measurement:
 * is assigned to a node,
 * might be part of a sequence,
 * has a start and endtime, duration is endtime-starttime
 * and a result (OK or Exception)
 *
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class RuntimePerformance implements Measurement {
    public enum ExecutionResult {
        OK, EXCEPTION
    }

    private Node node;
    private long nanoStarttime;
    private long nanoEndtime;
    private String sequence;
    private ExecutionResult executionResult;


    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public long getNanoStarttime() {
        return nanoStarttime;
    }

    public void setNanoStarttime(long nanoStarttime) {
        this.nanoStarttime = nanoStarttime;
    }

    public long getNanoEndtime() {
        return nanoEndtime;
    }

    public void setNanoEndtime(long nanoEndtime) {
        this.nanoEndtime = nanoEndtime;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public long getDuration() {
        return nanoEndtime - nanoStarttime;
    }

    public ExecutionResult getExecutionResult() {
        return executionResult;
    }

    public void setExecutionResult(ExecutionResult executionResult) {
        this.executionResult = executionResult;
    }

    @Override
    public String toString() {
        return "RuntimePerformance[" + node + "," + sequence + "," + nanoStarttime + "," + nanoEndtime + "," + getDuration() + "," + executionResult + "]";
    }
}
