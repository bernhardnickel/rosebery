package at.ac.tuwien.infosys.rosebery.scenario.provided;

import at.ac.tuwien.infosys.rosebery.scenario.Factory;
import at.ac.tuwien.infosys.rosebery.scenario.PopulationService;
import at.ac.tuwien.infosys.rosebery.scenario.Scenario;

import java.util.Arrays;
import java.util.List;

/**
 * List scenario
 * Runs a list of given scenarios in the provided order
 *
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class ListScenario<T> implements Scenario<T> {
    private List<Scenario<T>> scenarios;

    ListScenario(Scenario<T>... scenarios) {
        this.scenarios = Arrays.asList(scenarios);
    }

    @Override
    public void run(Factory<T> factory, PopulationService<T> populationService) {
        for (Scenario<T> scenario : scenarios) {
            scenario.run(factory, populationService);
        }
    }
}
