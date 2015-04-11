package at.ac.tuwien.infosys.rosebery.test.storm1;

import at.ac.tuwien.infosys.rosebery.scenario.Factory;
import at.ac.tuwien.infosys.rosebery.scenario.Scenario;
import at.ac.tuwien.infosys.rosebery.scenario.provided.LoopScenario;
import at.ac.tuwien.infosys.rosebery.scenario.provided.RunOnceScenario;
import at.ac.tuwien.infosys.rosebery.storm.spout.RoseberySpout;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

import java.util.Map;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class RoseberyTestSpout extends RoseberySpout<String> {
    public RoseberyTestSpout() {
        setFieldName("testField");
        setFactory(new Factory<String>() {
            @Override
            public String create() {
                return "testScenario-" + System.currentTimeMillis();
            }
        });
        setScenario(new LoopScenario<String>(new RunOnceScenario<String>(), 100, 100l));
    }
}
