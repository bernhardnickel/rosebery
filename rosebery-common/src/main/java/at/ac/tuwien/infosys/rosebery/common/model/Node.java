package at.ac.tuwien.infosys.rosebery.common.model;

import java.io.Serializable;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class Node implements Serializable {
    private String nodeId;
    private String nodePurpose;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodePurpose() {
        return nodePurpose;
    }

    public void setNodePurpose(String nodePurpose) {
        this.nodePurpose = nodePurpose;
    }

    @Override
    public String toString() {
        return "Node[" + nodeId + "," + nodePurpose + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) {
            return false;
        }

        Node node = (Node)obj;

        return (this.nodeId != null ? this.nodeId.equals(node.nodeId) : node.nodeId == null) &&
                (this.nodePurpose != null ? this.nodePurpose.equals(node.nodePurpose) : node.nodePurpose == null);
    }
}
