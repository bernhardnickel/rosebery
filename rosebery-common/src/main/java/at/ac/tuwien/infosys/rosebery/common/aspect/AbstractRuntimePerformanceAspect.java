package at.ac.tuwien.infosys.rosebery.common.aspect;

import at.ac.tuwien.infosys.rosebery.common.model.Node;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import at.ac.tuwien.infosys.rosebery.common.service.publication.DefaultPublicationService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Date;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public abstract class AbstractRuntimePerformanceAspect {
    @Pointcut
    abstract void scope();

    @Around("scope()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object o = null;
        Throwable t = null;

        RuntimePerformance rt = new RuntimePerformance();

        rt.setNanoStarttime(System.nanoTime());

        try {
            o = pjp.proceed();
        } catch (Throwable throwable) {
            t = throwable;
        }

        rt.setNanoEndtime(System.nanoTime());
        rt.setExecutionResult(t != null ? RuntimePerformance.ExecutionResult.OK : RuntimePerformance.ExecutionResult.EXCEPTION);
        rt.setNode(new Node());
        rt.getNode().setNodeId("testNode");
        rt.getNode().setNodePurpose("testPurpose");

        DefaultPublicationService.getInstance().publish(rt);


        if (t != null) {
            throw t;
        }

        return o;
    }
}
