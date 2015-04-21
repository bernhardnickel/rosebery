package at.ac.tuwien.infosys.rosebery.test.model;

import java.io.Serializable;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class DistanceObject implements Serializable {
    String id;
    Double distance;

    public DistanceObject(String id, Double distance) {
        this.id = id;
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public Double getDistance() {
        return distance;
    }
}
