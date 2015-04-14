package at.ac.tuwien.infosys.rosebery.test.storm1;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class TestBoltA extends BaseBasicBolt {
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String val = input.getString(0);
        System.out.println("StormTest1-BoltA:" + val);

        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {

        }


        collector.emit(new Values(val + "tbA"));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("testField"));
    }
}
