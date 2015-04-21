package at.ac.tuwien.infosys.rosebery.test.model;

import java.io.Serializable;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class NodeString implements Serializable {
    private String nodeString;

    public NodeString(String nodeString) {
        this.nodeString = nodeString;
    }

    public String getNodeString() {
        return nodeString;
    }

    @Override
    public String toString() {
        return nodeString;
    }
}
