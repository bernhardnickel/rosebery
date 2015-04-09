package at.ac.tuwien.infosys.rosebery.test.storm1;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class TestBolt extends BaseBasicBolt {
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        System.out.println("StormTest1: BOLT-OUT:" + input.getString(0));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
