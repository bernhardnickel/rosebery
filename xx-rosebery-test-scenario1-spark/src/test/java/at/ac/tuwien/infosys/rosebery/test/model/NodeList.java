package at.ac.tuwien.infosys.rosebery.test.model;

import java.util.List;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class NodeList {
    private List<Node> nodes;

    public NodeList(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        return nodes.toString();
    }
}
