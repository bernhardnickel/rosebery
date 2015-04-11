package at.ac.tuwien.infosys.rosebery.spark.receiver;

import at.ac.tuwien.infosys.rosebery.scenario.Factory;
import at.ac.tuwien.infosys.rosebery.scenario.PopulationService;
import at.ac.tuwien.infosys.rosebery.scenario.Scenario;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.receiver.Receiver;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class RoseberyReceiver<T> extends Receiver<T> {
    private Factory<T> factory;
    private Scenario<T> scenario;

    public RoseberyReceiver(StorageLevel storageLevel) {
        super(storageLevel);
    }

    public RoseberyReceiver(StorageLevel storageLevel, Factory<T> factory, Scenario<T> scenario) {
        super(storageLevel);
        this.factory = factory;
        this.scenario = scenario;
    }

    private PopulationService<T> populationService = new PopulationService<T>() {
        @Override
        public void populate(T t) {
            store(t);
        }
    };

    @Override
    public void onStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                scenario.run(factory, populationService);
            }
        }).start();
    }

    @Override
    public void onStop() {

    }

    public void setFactory(Factory<T> factory) {
        this.factory = factory;
    }

    public void setScenario(Scenario<T> scenario) {
        this.scenario = scenario;
    }
}
