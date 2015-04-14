package at.ac.tuwien.infosys.rosebery.test.spark1.aspect;

import at.ac.tuwien.infosys.rosebery.common.aspect.sequence.SequencedObject;
import at.ac.tuwien.infosys.rosebery.common.aspect.sequence.SequencedObjectImpl;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareMixin;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public class SequencedTestObject {
    @DeclareMixin("at.ac.tuwien.infosys.rosebery.test.spark1.TestObject")
    public static SequencedObject createSequencedTuple() {
        return new SequencedObjectImpl();
    }
}
