package at.ac.tuwien.infosys.rosebery.profiling;

import at.ac.tuwien.infosys.rosebery.common.aspect.RuntimePerformanceMeter;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.profiling.ExecutionProfile;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.profiling.ResourceSnapshot;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Abstract aspect for profiling
 * Starts a profiling thread during the execution of a pointcut
 * Snapshots are taken in a configured interval
 * at the end of the pointcut execution the thread is stopped
 * and the resource snapshots get published
 *
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public abstract class AbstractProfilingAspect {

    private static final String INTERVAL_SYSTEM_PROPERTY = "rosebery.profilingInterval";

    private long interval = Long.valueOf(System.getProperty(INTERVAL_SYSTEM_PROPERTY));

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 100,10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    @Pointcut
    public abstract void scope();


    @Around("scope() && this(jpo)")
    public Object around(ProceedingJoinPoint pjp, Object jpo) throws Throwable {
        //Start profiling for this pointcut
        ProfilingRunnable profilingThread = startProfiling(Thread.currentThread().getId(), interval);

        ExecutionProfile ep = new ExecutionProfile();

        RuntimePerformanceMeter meter = new RuntimePerformanceMeter(ep);
        meter.meter(pjp, jpo);

        //End and set resource snapshot from the profiling thread
        ep.setSnapshots(stopProfiling(profilingThread));

        PublicationService.getPublicationService().publish(ep);

        if (meter.getThrowable() != null) {
            throw meter.getThrowable();
        }

        return meter.getResult();
    }

    private ProfilingRunnable startProfiling(long threadId, long interval) {
        ProfilingRunnable runnable = new ProfilingRunnable();
        runnable.setThreadId(threadId);
        runnable.setInterval(interval);
        executor.execute(runnable);
        return runnable;
    }

    private Set<ResourceSnapshot> stopProfiling(ProfilingRunnable runnable) {
        runnable.interrupt();
        return runnable.getResult();
    }
}
