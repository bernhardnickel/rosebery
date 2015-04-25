package at.ac.tuwien.infosys.rosebery.common.aspect.sequence;

import java.io.Serializable;

/**
 * Interface for sequenced objects
 * Sequenced objects are created via AspectJ mixin
 *
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public interface SequencedObject extends Serializable {
    public String getSequence();
    public void setSequence(String sequence);
}
