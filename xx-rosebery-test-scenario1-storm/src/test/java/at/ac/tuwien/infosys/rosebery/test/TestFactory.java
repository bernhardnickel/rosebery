package at.ac.tuwien.infosys.rosebery.test;

import at.ac.tuwien.infosys.rosebery.scenario.Factory;

import java.util.Random;
import java.util.UUID;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class TestFactory implements Factory<String> {
    private Random random = new Random();

    @Override
    public String create() {
        StringBuilder str= new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int n = random.nextInt(5);
            for(int j = 0; j < n; j++) {
                str.append(UUID.randomUUID().toString());
            }
            str.append("a[").append(random.nextInt(100)).append(",").append(random.nextInt(100)).append("]");
        }

        return str.toString();
    }

    public static void main(String... args) {
        TestFactory fac = new TestFactory();
        System.out.print(fac.create());
    }
}
