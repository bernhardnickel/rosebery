package at.ac.tuwien.infosys.rosebery.test;

import at.ac.tuwien.infosys.rosebery.test.model.Node;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class DistanceBolt extends BaseBasicBolt {
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String id = (String)input.getValueByField("id");
        List<Node> nodes = (List<Node>)input.getValueByField("nodes");
        List<List<Integer>> paths = (List<List<Integer>>)input.getValueByField("paths");

        Map<Integer, Node> nodeMap = new HashMap<>();

        for (Node node : nodes) {
            nodeMap.put(node.getId(), node);
        }

        double minDistance = Double.MAX_VALUE;

        for (List<Integer> path : paths) {
            double pathDistance = 0d;

            for (int i = 0;  i < path.size() - 1; i++) {
                Node a = nodeMap.get(path.get(i));
                Node b = nodeMap.get(path.get(i + 1));

                int xd = Math.max(a.getX(), b.getX()) - Math.min(a.getX(), b.getX());
                int yd = Math.max(a.getY(), b.getY()) - Math.min(a.getY(), b.getY());
                double distance = Math.sqrt(Math.pow(xd, 2d) + Math.pow(yd, 2));

                pathDistance += distance;
            }

            if (pathDistance < minDistance) {
                minDistance = pathDistance;
            }
        }

        collector.emit(new Values(id, minDistance));
    }


    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("id", "distance"));
    }
}
