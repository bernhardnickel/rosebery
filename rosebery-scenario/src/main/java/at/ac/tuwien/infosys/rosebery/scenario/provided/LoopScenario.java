package at.ac.tuwien.infosys.rosebery.scenario.provided;

import at.ac.tuwien.infosys.rosebery.scenario.Factory;
import at.ac.tuwien.infosys.rosebery.scenario.PopulationService;
import at.ac.tuwien.infosys.rosebery.scenario.Scenario;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class LoopScenario<T> implements Scenario<T> {
    private Scenario<T> scenario;
    private long loopCount;
    private Long sleepTime = null;

    public LoopScenario(long loopCount, Long sleepTime) {
        this.loopCount = loopCount;
        this.sleepTime = sleepTime;
        this.scenario = new RunOnceScenario<>();
    }

    public LoopScenario(long loopCount) {
        this.loopCount = loopCount;
        this.scenario = new RunOnceScenario<>();
    }

    public LoopScenario(Scenario<T> scenario, long loopCount) {
        this.loopCount = loopCount;
        this.scenario = scenario;
    }

    public LoopScenario(Scenario<T> scenario, long loopCount, Long sleepTime) {
        this.scenario = scenario;
        this.loopCount = loopCount;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run(Factory<T> factory, PopulationService<T> populationService) {
        for (long i = 0; i < loopCount; i++) {
            scenario.run(factory, populationService);

            if (sleepTime != null) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {}
            }
        }
    }
}
