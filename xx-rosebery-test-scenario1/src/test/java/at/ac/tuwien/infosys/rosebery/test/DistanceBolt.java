package at.ac.tuwien.infosys.rosebery.test;

import at.ac.tuwien.infosys.rosebery.test.model.Node;
import at.ac.tuwien.infosys.rosebery.test.model.Vertex;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class DistanceBolt extends BaseBasicBolt {
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String id = (String)input.getValueByField("id");
        List<List<Node>> paths = (List<List<Node>>)input.getValueByField("paths");

        double minDistance = Double.MAX_VALUE;

        for (List<Node> path : paths) {
            double pathDistance = 0d;

            for (int i = 0;  i < path.size() - 1; i++) {
                Node a = path.get(i);
                Node b = path.get(i + 1);

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
