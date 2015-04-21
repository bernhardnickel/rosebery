package at.ac.tuwien.infosys.rosebery.test;

import at.ac.tuwien.infosys.rosebery.scenario.Factory;
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
public class ScenarioTopology {
    public static void main(String... args) {
        //Set system properties in code for test
        System.setProperty("rosebery.nodeFactory", "at.ac.tuwien.infosys.rosebery.common.factory.node.PropertyFileNodeFactory");
        System.setProperty("rosebery.nodeFactoryFile", "src/test/resources/nodes.properties");

        System.setProperty("rosebery.publicationService", "at.ac.tuwien.infosys.rosebery.common.service.publication.DefaultPublicationService:at.ac.tuwien.infosys.rosebery.transport.jdbc.JdbcPublicationService");

        System.setProperty("rosbery.jdbcConfiguration", "src/test/resources/jdbc.properties");

        System.setProperty("rosebery.JvmProfilingInterval", "1000");
        System.setProperty("rosebery.JvmProfilingPollingInterval", "100");

        System.setProperty("rosebery.publicationMode", "QUEUE");

        ScenarioTopology topology = new ScenarioTopology();
        topology.start();
    }

    protected void start() {
        LocalCluster cluster = new LocalCluster();

        Config config = new Config();
        config.setDebug(true);
        config.setMaxTaskParallelism(3);

        cluster.submitTopology("scenario1-topology", config, createTestTopology());
    }

    protected StormTopology createTestTopology() {
        TopologyBuilder builder = new TopologyBuilder();

        RoseberySpout<String> spout = new RoseberySpout<>();
        spout.setFieldName("inputString");
        spout.setFactory(new TestFactory());
        spout.setScenario(ScenarioDsl.evaluate("loop(1, 10)"));

        builder.setSpout("scenario1-spout", spout);

        builder.setBolt("scenario1-extractionBolt", new ExtractionBolt()).shuffleGrouping("scenario1-spout");
        builder.setBolt("scenario1-pathBolt", new PathBolt()).shuffleGrouping("scenario1-extractionBolt");
        builder.setBolt("scenario1-distanceBolt", new DistanceBolt()).shuffleGrouping("scenario1-pathBolt");
        builder.setBolt("scenario1-summaryBolt", new SummaryBolt()).shuffleGrouping("scenario1-distanceBolt");


        return builder.createTopology();
    }
}
