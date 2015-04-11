package at.ac.tuwien.infosys.rosebery.profiling;

import at.ac.tuwien.infosys.rosebery.common.aspect.RuntimePerformanceMeter;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.profiling.ExecutionProfile;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.profiling.ResourceSnapshot;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Set;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public abstract class AbstractProfilingAspect {

    @Pointcut
    public abstract void scope();


    public Object around(ProceedingJoinPoint pjp, Object jpo) throws Throwable {
        ProfilingThread profilingThread = startProfiling(Thread.currentThread().getId(), 10l);

        ExecutionProfile ep = new ExecutionProfile();

        RuntimePerformanceMeter meter = new RuntimePerformanceMeter(ep);
        meter.meter(pjp, jpo);

        ep.setSnapshots(stopProfiling(profilingThread));

        PublicationService.getPublicationService().publish(ep);

        if (meter.getThrowable() != null) {
            throw meter.getThrowable();
        }

        return meter.getResult();
    }

    private ProfilingThread startProfiling(long threadId, long interval) {
        ProfilingThread thread = new ProfilingThread();
        thread.setThreadId(threadId);
        thread.setInterval(interval);
        thread.start();
        return thread;
    }

    private Set<ResourceSnapshot> stopProfiling(ProfilingThread thread) {
        thread.interrupt();
        return thread.getResult();
    }
}
