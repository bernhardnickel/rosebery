package at.ac.tuwien.infosys.rosebery.test;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class SummaryBolt extends BaseBasicBolt {
    private Map<String, Double> minDistances = new HashMap<>();

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String id = (String)input.getValueByField("id");
        Double distance = (Double)input.getValueByField("distance");

        Double minDistance = minDistances.get(id);

        if (minDistance == null || distance < minDistance) {
            System.out.println("Found new min distance for id " + id + ": " + distance);
            minDistances.put(id, distance);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
