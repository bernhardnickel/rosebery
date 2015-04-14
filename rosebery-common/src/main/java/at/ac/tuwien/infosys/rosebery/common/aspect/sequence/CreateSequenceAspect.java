package at.ac.tuwien.infosys.rosebery.common.aspect.sequence;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.UUID;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public abstract class CreateSequenceAspect {
    @Pointcut
    public abstract void created(Object o);


    public void createSequence(Object o) {
        if (o instanceof SequencedObject) {
            ((SequencedObject)o).setSequence(UUID.randomUUID().toString());
        }
    }
}
