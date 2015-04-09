package at.ac.tuwien.infosys.rosebery.common.model.measurement;

import at.ac.tuwien.infosys.rosebery.common.model.Node;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class RuntimePerformance implements Measurement {
    public enum ExecutionResult {
        OK, EXCEPTION
    }

    private Node node;
    private long nanoStarttime;
    private long nanoEndtime;
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

    public ExecutionResult getExecutionResult() {
        return executionResult;
    }

    public void setExecutionResult(ExecutionResult executionResult) {
        this.executionResult = executionResult;
    }
}
