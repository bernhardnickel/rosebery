package at.ac.tuwien.infosys.rosebery.test;

import at.ac.tuwien.infosys.rosebery.scenario.dsl.ScenarioDsl;
import at.ac.tuwien.infosys.rosebery.spark.receiver.RoseberyReceiver;
import at.ac.tuwien.infosys.rosebery.test.model.NodeString;
import org.apache.spark.SparkConf;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.receiver.Receiver;

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
public class ScenarioRunner {
    public static void main(String... args) {
        //Set system properties in code for test
        System.setProperty("rosebery.nodeFactory", "at.ac.tuwien.infosys.rosebery.common.factory.node.PropertyFileNodeFactory");
        System.setProperty("rosebery.nodeFactoryFile", "src/test/resources/nodes.properties");

        StringBuilder publicationServices = new StringBuilder();

        publicationServices.append("at.ac.tuwien.infosys.rosebery.transport.log4j.Log4jPublicationService");
        publicationServices.append(":").append("at.ac.tuwien.infosys.rosebery.transport.jdbc.JdbcPublicationService");

        System.setProperty("rosebery.publicationService", publicationServices.toString());

        System.setProperty("rosbery.jdbcConfiguration", "src/test/resources/jdbc.properties");

        System.setProperty("rosebery.JvmProfilingInterval", "1000");
        System.setProperty("rosebery.JvmProfilingPollingInterval", "100");

        System.setProperty("rosebery.publicationMode", "QUEUE");

        ScenarioRunner runner = new ScenarioRunner();
        runner.run();
    }

    protected void run() {
        SparkConf conf = new SparkConf();
        //Should be set > 1 if there is a receiver. Why? No idea! :D
        conf.setMaster("local[2]");
        conf.set("spark.streaming.concurrentJobs", "3");
        conf.set("spark.streaming.unpersist", "false");

        conf.setAppName("sparkTestApplication");

        JavaStreamingContext ssc = new JavaStreamingContext(conf, new Duration(7000));

        Receiver<NodeString> receiver = new RoseberyReceiver<NodeString>(StorageLevel.MEMORY_AND_DISK_SER(),
                new TestFactory(),
                ScenarioDsl.evaluate("loop(25, 10)")
        );

        JavaDStream<NodeString> testStream = ssc.receiverStream(receiver);

        testStream.map(
                new ExtractionFunction()).flatMap(
                new PathFunction()).map(
                new DistanceFunction()).foreachRDD(new SummaryFunction());

        ssc.start();
        ssc.awaitTermination();
    }
}
