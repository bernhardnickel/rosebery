package at.ac.tuwien.infosys.rosebery.test.spark1;

import org.apache.spark.api.java.function.Function;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class TestFunctionB implements Function<TestObject, TestObject> {
    @Override
    public TestObject call(TestObject v1) throws Exception {
        System.out.println("SparkTest1-TestFunctionB:" + v1);
        return new TestObject(v1 + "-tfB");
    }
}
