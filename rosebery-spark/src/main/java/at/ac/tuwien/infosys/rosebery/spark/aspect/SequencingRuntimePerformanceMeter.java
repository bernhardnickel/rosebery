package at.ac.tuwien.infosys.rosebery.spark.aspect;

import at.ac.tuwien.infosys.rosebery.common.aspect.RuntimePerformanceMeter;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class SequencingRuntimePerformanceMeter extends RuntimePerformanceMeter {
    @Override
    protected Object proceed(ProceedingJoinPoint pjp) throws Throwable {
        String sequence = null;
        Object[] args = new Object[pjp.getArgs().length];

        for (int i = 0; i < pjp.getArgs().length; i++) {
            Object arg = pjp.getArgs()[i];

            if (arg instanceof SequencedObject) {
                if (sequence == null) {
                    sequence = ((SequencedObject)arg).getSequence();
                }
                arg = ((SequencedObject)arg).getObject();
            }

            args[i] = arg;
        }

        Object res = pjp.proceed(args);

        if (sequence != null) {
            res = new SequencedObject(sequence, res);
        }

        return res;
    }
}
