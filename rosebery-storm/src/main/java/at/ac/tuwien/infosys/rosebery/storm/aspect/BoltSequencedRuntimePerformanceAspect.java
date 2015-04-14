package at.ac.tuwien.infosys.rosebery.storm.aspect;

import at.ac.tuwien.infosys.rosebery.common.aspect.sequence.SequencedRuntimePerformanceAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public class BoltSequencedRuntimePerformanceAspect extends SequencedRuntimePerformanceAspect {

    @Override
    @Pointcut("execution(* backtype.storm.topology.IBasicBolt.execute(backtype.storm.tuple.Tuple, ..)) && args(o, *)")
    public void scope(Object o) {}

    @Override
    public Object around(ProceedingJoinPoint pjp, Object jpo, Object o) throws Throwable {
        return super.around(pjp, jpo, o);
    }
}
