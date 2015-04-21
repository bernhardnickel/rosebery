package at.ac.tuwien.infosys.rosebery.test;

import at.ac.tuwien.infosys.rosebery.test.model.DistanceObject;
import at.ac.tuwien.infosys.rosebery.test.model.Node;
import at.ac.tuwien.infosys.rosebery.test.model.PathObject;
import org.apache.spark.api.java.function.Function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class DistanceFunction implements Function<PathObject, DistanceObject> {

    @Override
    public DistanceObject call(PathObject pathObject) throws Exception {
        System.out.println("Recevied path object: " + pathObject);

        String id = pathObject.getId();
        List<Node> nodes = pathObject.getNodes();
        List<List<Integer>> paths = pathObject.getPaths();

        Map<Integer, Node> nodeMap = new HashMap<>();

        for (Node node : nodes) {
            nodeMap.put(node.getId(), node);
        }

        double minDistance = Double.MAX_VALUE;

        for (List<Integer> path : paths) {
            double pathDistance = 0d;

            for (int i = 0;  i < path.size() - 1; i++) {
                Node a = nodeMap.get(path.get(i));
                Node b = nodeMap.get(path.get(i + 1));

                int xd = Math.max(a.getX(), b.getX()) - Math.min(a.getX(), b.getX());
                int yd = Math.max(a.getY(), b.getY()) - Math.min(a.getY(), b.getY());
                double distance = Math.sqrt(Math.pow(xd, 2d) + Math.pow(yd, 2));

                pathDistance += distance;
            }

            if (pathDistance < minDistance) {
                minDistance = pathDistance;
            }
        }

        return new DistanceObject(id, minDistance);
    }
}
