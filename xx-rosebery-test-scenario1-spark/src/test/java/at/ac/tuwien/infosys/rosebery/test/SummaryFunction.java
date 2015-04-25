package at.ac.tuwien.infosys.rosebery.test;

import at.ac.tuwien.infosys.rosebery.test.model.DistanceObject;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class SummaryFunction implements Function<JavaRDD<DistanceObject>, Void> {
    private static final Logger logger = Logger.getLogger(SummaryFunction.class);

    private final Map<String, Double> minDistances = new HashMap<>();


    @Override
    public Void call(JavaRDD<DistanceObject> rdd) throws Exception {
        for (DistanceObject distanceObject : rdd.collect()) {
            String id = distanceObject.getId();
            Double distance = distanceObject.getDistance();

            Double minDistance = minDistances.get(id);

            if (minDistance == null || distance < minDistance) {
                logger.info("Found new min distance for id " + id + ": " + distance);

                minDistances.put(id, distance);
            }logger.info(minDistances.size() + " min distance sequences found");
        }

        logger.info(minDistances.size() + " min distance sequences found");

        return null;
    }
}
