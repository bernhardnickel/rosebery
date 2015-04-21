package at.ac.tuwien.infosys.rosebery.spark.aspect;

import at.ac.tuwien.infosys.rosebery.common.aspect.sequence.SequencedRuntimePerformanceAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public class ReceiverRuntimePerformanceAspect extends SequencedRuntimePerformanceAspect {
    @Override
    @Pointcut("call(* org.apache.spark.streaming.receiver.Receiver.store(java.lang.Object)) && args(o)")
    public void scope(Object o) {}


    @Override
    @Around("scope(o) && target(jpo)")
    public Object around(ProceedingJoinPoint pjp, Object jpo, Object o) throws Throwable {
        return super.around(pjp, jpo, o);
    }
}
