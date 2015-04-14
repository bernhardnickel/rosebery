package at.ac.tuwien.infosys.rosebery.common.aspect.sequence;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public abstract class SequencePassOnAspect {

    @Pointcut
    public abstract void finished(Object in, Object out);

    public void assignSequence(Object in, Object out) {
        if (in instanceof SequencedObject) {
            if (out instanceof SequencedObject) {
                ((SequencedObject)out).setSequence(((SequencedObject)in).getSequence());
            }
        }
    }
}
