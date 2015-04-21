package at.ac.tuwien.infosys.rosebery.test;

import at.ac.tuwien.infosys.rosebery.test.model.Node;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class PathBolt extends BaseBasicBolt {

    private static final int MAX_PATHS = 200000;

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        List<Node> nodes = (List<Node>)input.getValueByField("nodeList");

        List<List<Integer>> paths = new ArrayList<>();

        permute(nodes, new ArrayList<>(), 0, paths);

        String id = UUID.randomUUID().toString();

        int i = 0;

        for (; (i + MAX_PATHS) < paths.size(); i += MAX_PATHS) {
            collector.emit(new Values(id, nodes, paths.subList(i, i + MAX_PATHS)));
        }

        collector.emit(new Values(id, nodes, paths.subList(i, paths.size())));
    }

    private void permute(List<Node> lst, List<Integer> h, int i, List<List<Integer>> r) {
        if (i == lst.size() - 1) {
            for (int j = 0; j < lst.size(); j++) {
                if (!h.contains(lst.get(j).getId())) {
                    List<Integer> nh = new ArrayList<>(h);
                    nh.add(lst.get(j).getId());
                    r.add(nh);
                }
            }
            return;
        }

        for (int j = 0; j < lst.size(); j++) {
            if (!h.contains(lst.get(j).getId())) {
                List<Integer> nh = new ArrayList<>(h);
                nh.add(lst.get(j).getId());
                permute(lst, nh, i + 1, r);
            }
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("id", "nodes", "paths"));
    }
}
