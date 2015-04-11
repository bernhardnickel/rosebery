package at.ac.tuwien.infosys.rosebery.scenario.provided;

import at.ac.tuwien.infosys.rosebery.scenario.Factory;
import at.ac.tuwien.infosys.rosebery.scenario.PopulationService;
import at.ac.tuwien.infosys.rosebery.scenario.Scenario;

import java.util.Random;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class RandomScenario<T> implements Scenario<T> {
    private Random random = new Random();
    private Scenario<T> [] scenarios;

    public RandomScenario(Scenario<T>... scenarios) {
        this.scenarios = scenarios;
    }

    @Override
    public void run(Factory<T> factory, PopulationService<T> populationService) {
        scenarios[random.nextInt(scenarios.length)].run(factory, populationService);
    }
}
