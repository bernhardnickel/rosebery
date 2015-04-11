package at.ac.tuwien.infosys.rosebery.common.aspect;

import at.ac.tuwien.infosys.rosebery.common.factory.node.NodeFactory;
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
public abstract class AbstractRuntimePerformanceAspect {
    @Pointcut
    public abstract void scope();

    @Around("scope() && this(jpo)")
    public Object around(ProceedingJoinPoint pjp, Object jpo) throws Throwable {
        RuntimePerformanceMeter meter = getMeter();

        meter.meter(pjp, jpo);

        RuntimePerformance rt = meter.getRuntimePerformance();

        PublicationService.getPublicationService().publish(rt);

        if (meter.getThrowable() != null) {
            throw meter.getThrowable();
        }

        return meter.getResult();
    }

    protected RuntimePerformanceMeter getMeter() {
        return new RuntimePerformanceMeter();
    }
}
