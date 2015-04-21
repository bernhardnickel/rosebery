package at.ac.tuwien.infosys.rosebery.test.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class PathObject implements Serializable {
    String id;
    List<Node> nodes;
    List<List<Integer>> paths;

    public PathObject(String id, List<Node> nodes, List<List<Integer>> paths) {
        this.id = id;
        this.nodes = nodes;
        this.paths = paths;
    }


    public String getId() {
        return id;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<List<Integer>> getPaths() {
        return paths;
    }

    @Override
    public String toString() {
        return id + "-" + paths.toString();
    }
}
