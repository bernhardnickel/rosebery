package at.ac.tuwien.infosys.rosebery.spark.aspect;

import at.ac.tuwien.infosys.rosebery.common.aspect.sequence.SequencePassOnAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public class FunctionSequencePassOnAspect extends SequencePassOnAspect {

    @Override
    @Pointcut("execution(* org.apache.spark.api.java.function.Function.call(java.lang.Object)) && args(in) && this(out)")
    public void finished(Object in, Object out) {}

    @Around("finished(in, out)")
    public Object assignSequence(ProceedingJoinPoint pjp, Object in, Object out) throws Throwable{
        out = pjp.proceed();

        super.assignSequence(in, out);

        return out;
    }
}
