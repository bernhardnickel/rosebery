package at.ac.tuwien.infosys.rosebery.test.spark1;

import org.apache.spark.api.java.function.Function;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class TestFunctionA implements Function<String, String> {
    @Override
    public String call(String v1) throws Exception {
        System.out.println("SparkTest1-TestFunctionA:" + v1);
        return v1 + "-tfA";
    }
}