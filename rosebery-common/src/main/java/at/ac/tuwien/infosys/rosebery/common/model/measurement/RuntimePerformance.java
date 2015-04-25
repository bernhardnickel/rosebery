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
    private long starttime;
    private long endtime;
    private long duration;
    private String sequence;
    private ExecutionResult executionResult;

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public ExecutionResult getExecutionResult() {
        return executionResult;
    }

    public void setExecutionResult(ExecutionResult executionResult) {
        this.executionResult = executionResult;
    }

    @Override
    public String toString() {
        return "RuntimePerformance[" + node + "," + sequence + "," + starttime + "," + endtime + "," + duration + "," + executionResult + "]";
    }
}
