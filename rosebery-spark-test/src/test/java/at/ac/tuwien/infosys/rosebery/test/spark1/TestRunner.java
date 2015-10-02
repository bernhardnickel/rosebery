package at.ac.tuwien.infosys.rosebery.test.spark1;

import at.ac.tuwien.infosys.rosebery.scenario.Factory;
import at.ac.tuwien.infosys.rosebery.scenario.dsl.ScenarioDsl;
import at.ac.tuwien.infosys.rosebery.spark.receiver.RoseberyReceiver;
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
 * -Drosebery.nodeFactoryFile=src/test/resources/at/ac/tuwien/infosys/rosebery/test/spark1/nodes.properties
 * -Drosebery.publicationService=at.ac.tuwien.infosys.rosebery.common.service.publication.DefaultPublicationService:MeasurementNotificationService
 */
public class TestRunner {
    public static void main(String... args) throws Exception {
        //Set system properties in code for test
        System.setProperty("rosebery.nodeFactory", "at.ac.tuwien.infosys.rosebery.common.factory.node.PropertyFileNodeFactory");
        System.setProperty("rosebery.nodeFactoryFile", "src/test/resources/at/ac/tuwien/infosys/rosebery/test/spark1/nodes.properties");

        System.setProperty("rosebery.publicationService", "at.ac.tuwien.infosys.rosebery.common.service.publication.DefaultPublicationService:MeasurementNotificationService");


        startSparkStreaming();
    }

    private static void startSparkStreaming() throws Exception {
        SparkConf conf = new SparkConf();
        //Should be set > 1 if there is a receiver. Why? No idea! :D
        conf.setMaster("local[2]");
        conf.set("spark.streaming.concurrentJobs", "3");

        conf.setAppName("sparkTestApplication");

        JavaStreamingContext ssc = new JavaStreamingContext(conf, new Duration(1000));

        Receiver<TestObject> receiver = new RoseberyReceiver<TestObject>(StorageLevel.MEMORY_ONLY_SER(),
                new Factory<TestObject>() {
                    @Override
                    public TestObject create() {
                        return new TestObject("rosberyTest-" + System.currentTimeMillis());
                    }
                },
                ScenarioDsl.evaluate("loop(10, 100)")
        );


        JavaDStream<TestObject> testStream = ssc.receiverStream(receiver);

        testStream.map(new TestFunctionA()).map(new TestFunctionB()).foreach(new PrintFunction());

        ssc.start();
        ssc.awaitTermination();
    }
}
