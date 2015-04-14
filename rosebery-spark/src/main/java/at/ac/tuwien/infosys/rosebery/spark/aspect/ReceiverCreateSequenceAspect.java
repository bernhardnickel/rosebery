package at.ac.tuwien.infosys.rosebery.spark.aspect;

import at.ac.tuwien.infosys.rosebery.common.aspect.sequence.CreateSequenceAspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public class ReceiverCreateSequenceAspect extends CreateSequenceAspect {

    @Override
    @Pointcut("call(* org.apache.spark.streaming.receiver.Receiver.store(java.lang.Object)) && args(o)")
    public void created(Object o) {}

    @Before("created(o)")
    public void createSequence(Object o)  {
        super.createSequence(o);
    }
}
