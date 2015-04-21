package at.ac.tuwien.infosys.rosebery.test.aspect;

import at.ac.tuwien.infosys.rosebery.common.aspect.sequence.SequencedObject;
import at.ac.tuwien.infosys.rosebery.common.aspect.sequence.SequencedObjectImpl;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareMixin;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public class SequencedObjectsAspect {
    @DeclareMixin("at.ac.tuwien.infosys.rosebery.test.model.NodeString")
    public static SequencedObject createSequencedNodeString() {
        return new SequencedObjectImpl();
    }

    @DeclareMixin("at.ac.tuwien.infosys.rosebery.test.model.NodeList")
    public static SequencedObject createSequencedNodeList() {
        return new SequencedObjectImpl();
    }

    @DeclareMixin("at.ac.tuwien.infosys.rosebery.test.model.PathObject")
    public static SequencedObject createSequencedPathObject() {
        return new SequencedObjectImpl();
    }

    @DeclareMixin("at.ac.tuwien.infosys.rosebery.test.model.DistanceObject")
    public static SequencedObject createSequencedDistanceObject() {
        return new SequencedObjectImpl();
    }
}
