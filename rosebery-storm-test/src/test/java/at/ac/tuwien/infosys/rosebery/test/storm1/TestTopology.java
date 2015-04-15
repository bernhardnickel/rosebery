package at.ac.tuwien.infosys.rosebery.test.storm1;

import at.ac.tuwien.infosys.rosebery.scenario.Factory;
import at.ac.tuwien.infosys.rosebery.scenario.Scenario;
import at.ac.tuwien.infosys.rosebery.scenario.dsl.ScenarioDsl;
import at.ac.tuwien.infosys.rosebery.storm.spout.RoseberySpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 *
 *
 * Run with VM argument
 * -javaagent:/opt/aspectj/lib/aspectjweaver.jar
 * -Drosebery.nodeFactory=at.ac.tuwien.infosys.rosebery.common.factory.node.PropertyFileNodeFactory
 * -Drosebery.nodeFactoryFile=src/test/resources/at/ac/tuwien/infosys/rosebery/test/storm1/nodes.properties
 * -Drosebery.publicationService=at.ac.tuwien.infosys.rosebery.common.service.publication.DefaultPublicationService:at.ac.tuwien.infosys.rosebery.transport.jmx.MeasurementNotificationService
 *
 */
public class TestTopology {
    public static void main(String... args) {
        //Set system properties in code for test
        System.setProperty("rosebery.nodeFactory", "at.ac.tuwien.infosys.rosebery.common.factory.node.PropertyFileNodeFactory");
        System.setProperty("rosebery.nodeFactoryFile", "src/test/resources/at/ac/tuwien/infosys/rosebery/test/storm1/nodes.properties");

        System.setProperty("rosebery.publicationService", "at.ac.tuwien.infosys.rosebery.common.service.publication.DefaultPublicationService:at.ac.tuwien.infosys.rosebery.transport.jmx.MeasurementNotificationService");

        System.setProperty("rosebery.profilingInterval", "100");

        System.setProperty("rosebery.publicationMode", "QUEUE");

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
        RoseberySpout<String> spout = new RoseberySpout<>();
        spout.setFieldName("testField");
        spout.setFactory(new Factory<String>() {
            @Override
            public String create() {
                return "testScenario-" + System.currentTimeMillis();
            }
        });
        spout.setScenario(ScenarioDsl.evaluate("loop(loop(100, 100), 10)"));

        builder.setSpout("test-spout", spout);

        builder.setBolt("test-boltA", new TestBoltA()).shuffleGrouping("test-spout");
        builder.setBolt("test-boltB", new TestBoltB()).shuffleGrouping("test-boltA");


        return builder.createTopology();
    }
}
