package at.ac.tuwien.infosys.rosebery.storm.aspect;

import at.ac.tuwien.infosys.rosebery.common.aspect.sequence.SequencedObject;
import at.ac.tuwien.infosys.rosebery.common.aspect.sequence.SequencedObjectImpl;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.tuple.Tuple;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareMixin;
import org.aspectj.lang.annotation.Pointcut;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public class SequencedTupleAspect{
    @DeclareMixin("backtype.storm.tuple.TupleImpl")
    public static SequencedObject createSequencedTuple() {
        return new SequencedObjectImpl();
    }
}
