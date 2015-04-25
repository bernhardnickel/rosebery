package at.ac.tuwien.infosys.rosebery.scenario.provided;

import at.ac.tuwien.infosys.rosebery.scenario.Factory;
import at.ac.tuwien.infosys.rosebery.scenario.PopulationService;
import at.ac.tuwien.infosys.rosebery.scenario.Scenario;

/**
 * Runs the scenario only once
 *
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class RunOnceScenario<T> implements Scenario<T> {
    @Override
    public void run(Factory<T> factory, PopulationService<T> populationService) {
        populationService.populate(factory.create());
    }
}
