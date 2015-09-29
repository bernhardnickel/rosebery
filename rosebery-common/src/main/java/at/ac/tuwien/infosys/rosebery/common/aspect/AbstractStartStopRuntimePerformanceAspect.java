package at.ac.tuwien.infosys.rosebery.common.aspect;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * Abstract aspect to measure runtime performance of a method / flow
 *
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public abstract class AbstractStartStopRuntimePerformanceAspect {
    private ThreadLocal<StartStopRuntimePerformanceMeter> meter = new ThreadLocal<>();

    @Pointcut
    public abstract void startScope(Object jpo);

    @Pointcut
    public abstract void stopScope();

    @Before("startScope(jpo)")
    public void start(Object jpo) {
        meter.set(new StartStopRuntimePerformanceMeter());
        meter.get().start(jpo);
    }


    @AfterReturning(pointcut = "stopScope()", returning = "result")
    public void stop(Object result) {
        meter.get().stop(result);

        stop();
    }

    @AfterThrowing(pointcut = "stopScope()", throwing = "throwable")
    public void stop(Throwable throwable) {
        meter.get().stop(throwable);

        stop();
    }

    private void stop() {
        RuntimePerformance rt = meter.get().getRuntimePerformance();

        PublicationService.getPublicationService().publish(rt);
    }

    protected RuntimePerformanceMeter getMeter() {
        return new RuntimePerformanceMeter();
    }
}
