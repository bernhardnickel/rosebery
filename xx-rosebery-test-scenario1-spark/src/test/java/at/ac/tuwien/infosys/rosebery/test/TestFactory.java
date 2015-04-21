package at.ac.tuwien.infosys.rosebery.test;

import at.ac.tuwien.infosys.rosebery.scenario.Factory;
import at.ac.tuwien.infosys.rosebery.test.model.NodeString;

import java.util.Random;
import java.util.UUID;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class TestFactory implements Factory<NodeString> {
    private Random random = new Random();

    @Override
    public NodeString create() {
        StringBuilder str= new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int n = random.nextInt(100000);
            for(int j = 0; j < n; j++) {
                str.append(UUID.randomUUID().toString());
            }
            str.append("a[").append(random.nextInt(100)).append(",").append(random.nextInt(100)).append("]");
        }

        return new NodeString(str.toString());
    }
}
