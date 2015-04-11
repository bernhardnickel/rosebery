package at.ac.tuwien.infosys.rosebery.test.spark1;

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
 * -Drosebery.publicationService=CLASSPATH
 *
 */
public class TestRunner {
    public static void main(String... args) throws Exception {
        //Set system properties in code for test
        System.setProperty("rosebery.nodeFactory", "at.ac.tuwien.infosys.rosebery.common.factory.node.PropertyFileNodeFactory");
        System.setProperty("rosebery.nodeFactoryFile", "src/test/resources/at/ac/tuwien/infosys/rosebery/test/spark1/nodes.properties");
        System.setProperty("rosebery.publicationService", "CLASSPATH");

        startSparkStreaming();
    }

    private static void startSparkStreaming() throws Exception {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[1]");
        conf.set("spark.streaming.concurrentJobs", "3");

        conf.setAppName("sparkTestApplication");

        JavaStreamingContext ssc = new JavaStreamingContext(conf, new Duration(1000));

        Receiver<String> receiver = new RosberyTestReceiver(StorageLevel.MEMORY_ONLY_SER());

        JavaDStream<String> testStream = ssc.receiverStream(receiver);

        testStream.map(new TestFunctionA()).map(new TestFunctionB()).foreach(new PrintFunction());

        ssc.start();
        ssc.awaitTermination();
    }
}
