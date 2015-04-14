package at.ac.tuwien.infosys.rosebery.test.spark1;

import java.io.Serializable;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class TestObject implements Serializable {
    private String s;

    public TestObject() {
    }

    public TestObject(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
