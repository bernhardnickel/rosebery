package at.ac.tuwien.infosys.rosebery.test;

import at.ac.tuwien.infosys.rosebery.test.model.DistanceObject;
import org.apache.spark.api.java.function.Function;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class SummaryFunction implements Function<DistanceObject, Void> {
    private Map<String, Double> minDistances = new HashMap<>();

    @Override
    public Void call(DistanceObject distanceObject) throws Exception {
        String id = distanceObject.getId();
        Double distance = distanceObject.getDistance();

        Double minDistance = minDistances.get(id);

        if (minDistance == null || distance < minDistance) {
            System.out.println("Found new min distance for id " + id + ": " + distance);
            minDistances.put(id, distance);
        }

        return null;
    }
}
