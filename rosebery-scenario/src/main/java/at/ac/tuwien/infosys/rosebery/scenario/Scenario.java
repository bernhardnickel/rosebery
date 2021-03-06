package at.ac.tuwien.infosys.rosebery.scenario;

import java.io.Serializable;

/**
 * Scenario interface
 *
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public interface Scenario<T> extends Serializable {
    public void run(Factory<T> factory, PopulationService<T> populationService);
}
