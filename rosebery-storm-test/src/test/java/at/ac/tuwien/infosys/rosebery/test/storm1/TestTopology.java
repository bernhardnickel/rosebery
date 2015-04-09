package at.ac.tuwien.infosys.rosebery.test.storm1;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;

import java.io.File;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 *
 *
 * Run with VM argument
 * -javaagent:/opt/aspectj/lib/aspectjweaver.jar
 * -Drosebery.nodeFactory=at.ac.tuwien.infosys.rosebery.common.factory.node.PropertyFileNodeFactory
 * -Drosebery.nodeFactoryFile=src/test/resources/at/ac/tuwien/infosys/rosebery/test/storm1/nodes.properties
 * -Drosebery.publicationService=CLASSPATH
 *
 */
public class TestTopology {
    public static void main(String... args) {
        //Set system properties in code for test
        System.setProperty("rosebery.nodeFactory", "at.ac.tuwien.infosys.rosebery.common.factory.node.PropertyFileNodeFactory");
        System.setProperty("rosebery.nodeFactoryFile", "src/test/resources/at/ac/tuwien/infosys/rosebery/test/storm1/nodes.properties");
        System.setProperty("rosebery.publicationService", "CLASSPATH");

        startStorm();
    }

    protected static void startStorm() {
        LocalCluster cluster = new LocalCluster();

        Config config = new Config();
        config.setDebug(true);
        config.setMaxTaskParallelism(3);

        cluster.submitTopology("test-topology", config, createTestTopology());
    }

    protected static StormTopology createTestTopology() {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("test-spout", new TestSpout());
        builder.setBolt("test-bolt", new TestBolt()).shuffleGrouping("test-spout");


        return builder.createTopology();
    }
}
