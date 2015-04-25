package at.ac.tuwien.infosys.rosebery.common.aspect.sequence;

/**
 * SequencedObject implementation
 *
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class SequencedObjectImpl implements SequencedObject {
    private String sequence;

    @Override
    public String getSequence() {
        return sequence;
    }

    @Override
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
