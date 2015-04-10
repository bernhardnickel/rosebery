package at.ac.tuwien.infosys.rosebery.spark.aspect;

import java.io.Serializable;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class SequencedObject implements Serializable {
    private String sequence;
    private Object object;

    public SequencedObject(String sequence, Object object) {
        this.sequence = sequence;
        this.object = object;
    }

    public String getSequence() {
        return sequence;
    }

    public Object getObject() {
        return object;
    }
}
