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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class ExtractionBolt extends BaseBasicBolt {
    Pattern pattern = Pattern.compile("a\\[[0-9]+,[0-9]+\\]");

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String str = (String)input.getValueByField("inputString");

        List<Node> nodes = new ArrayList<>();

        Matcher m = pattern.matcher(str);

        while (m.find()) {
            String[] xy = m.group().replace("a[", "").replace("]", "").split(",");
            nodes.add(new Node(Integer.valueOf(xy[0]), Integer.valueOf(xy[1])));
        }

        collector.emit(new Values(nodes));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("nodeList"));
    }
}
