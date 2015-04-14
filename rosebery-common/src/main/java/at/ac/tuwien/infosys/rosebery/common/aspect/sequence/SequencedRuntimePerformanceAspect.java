package at.ac.tuwien.infosys.rosebery.common.aspect.sequence;

import at.ac.tuwien.infosys.rosebery.common.aspect.RuntimePerformanceMeter;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public abstract class SequencedRuntimePerformanceAspect {

    @Pointcut
    public abstract void scope(Object o);

    @Around("scope(o) && this(jpo)")
    public Object around(ProceedingJoinPoint pjp, Object jpo, Object o) throws Throwable {
        RuntimePerformanceMeter meter = new RuntimePerformanceMeter();

        String sequence = null;

        if (o instanceof SequencedObject) {
            sequence = ((SequencedObject)o).getSequence();
        }

        meter.meter(pjp, jpo, sequence);

        RuntimePerformance rt = meter.getRuntimePerformance();

        PublicationService.getPublicationService().publish(rt);


        if (meter.getThrowable() != null) {
            throw meter.getThrowable();
        }

        return meter.getResult();
    }
}
