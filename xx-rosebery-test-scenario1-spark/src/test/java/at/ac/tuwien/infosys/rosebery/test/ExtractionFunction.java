package at.ac.tuwien.infosys.rosebery.test;

import at.ac.tuwien.infosys.rosebery.test.model.Node;
import at.ac.tuwien.infosys.rosebery.test.model.NodeList;
import at.ac.tuwien.infosys.rosebery.test.model.NodeString;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.function.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class ExtractionFunction implements Function<NodeString, NodeList> {
    Pattern pattern = Pattern.compile("a\\[[0-9]+,[0-9]+\\]");

    @Override
    public NodeList call(NodeString nodeString) throws Exception {
        List<Node> nodes = new ArrayList<>();

        Matcher m = pattern.matcher(nodeString.getNodeString());

        int i = 0;
        while (m.find()) {
            String[] xy = m.group().replace("a[", "").replace("]", "").split(",");
            nodes.add(new Node(i++, Integer.valueOf(xy[0]), Integer.valueOf(xy[1])));
        }

        return new NodeList(nodes);
    }
}
