package at.ac.tuwien.infosys.rosebery.scenario.dsl;

import at.ac.tuwien.infosys.rosebery.scenario.Factory;
import at.ac.tuwien.infosys.rosebery.scenario.PopulationService;
import at.ac.tuwien.infosys.rosebery.scenario.Scenario;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class ScenarioDslTest {
    @Test
    public void test() {
        Scenario<String> scenario = ScenarioDsl.evaluate("list(" +
                "loop(3, 1000), " +
                "once())");

        final AtomicInteger counter = new AtomicInteger(0);

        scenario.run(new Factory<String>() {
            @Override
            public String create() {
                counter.incrementAndGet();
                return ScenarioDslTest.class.getName() + counter.toString();
            }
        }, new PopulationService<String>() {
            int c = 0;
            @Override
            public void populate(String s) {
                assertEquals(++c, counter.get());
                assertEquals(ScenarioDslTest.class.getName() + c, s);
            }
        });

        assertEquals(4, counter.get());
    }
}
