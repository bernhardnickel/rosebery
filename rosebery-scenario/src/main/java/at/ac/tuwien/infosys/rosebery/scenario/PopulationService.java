package at.ac.tuwien.infosys.rosebery.scenario;

import java.io.Serializable;

/**
 * Population service interface
 * Acts as the interface between the rosebery scenario package
 * and a distributed software system
 *
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public interface PopulationService<T> extends Serializable {
    public void populate(T t);
}
