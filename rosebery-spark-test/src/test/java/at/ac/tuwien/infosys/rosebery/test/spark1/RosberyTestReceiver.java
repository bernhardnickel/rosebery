package at.ac.tuwien.infosys.rosebery.test.spark1;

import at.ac.tuwien.infosys.rosebery.scenario.Factory;
import at.ac.tuwien.infosys.rosebery.scenario.provided.LoopScenario;
import at.ac.tuwien.infosys.rosebery.scenario.provided.RunOnceScenario;
import at.ac.tuwien.infosys.rosebery.spark.receiver.RoseberyReceiver;
import org.apache.spark.storage.StorageLevel;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class RosberyTestReceiver extends RoseberyReceiver<String> {
    public RosberyTestReceiver(StorageLevel storageLevel) {
        super(storageLevel);

        setFactory(new Factory<String>() {
            @Override
            public String create() {
                return "rosberyTest-" + System.currentTimeMillis();
            }
        });

        setScenario(new LoopScenario<String>(new RunOnceScenario<String>(), 10, 100l));
    }
}
