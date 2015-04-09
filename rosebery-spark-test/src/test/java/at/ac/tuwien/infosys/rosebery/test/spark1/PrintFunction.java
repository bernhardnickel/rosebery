package at.ac.tuwien.infosys.rosebery.test.spark1;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class PrintFunction implements Function<JavaRDD<String>,Void> {
    @Override
    public Void call(JavaRDD<String> v1) throws Exception {

        for (String s : v1.take(10)) {
            System.out.println("SparkTest1-PrintFunction:" + s);
        }

        return null;
    }
}
