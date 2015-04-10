package at.ac.tuwien.infosys.rosebery.common.aspect;

import at.ac.tuwien.infosys.rosebery.common.factory.node.NodeFactory;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class RuntimePerformanceMeter {

    private Object result;
    private Throwable throwable;

    public RuntimePerformance meter(ProceedingJoinPoint pjp, Object jpo, String sequence) {
        RuntimePerformance rt = new RuntimePerformance();

        rt.setSequence(sequence);
        rt.setNanoStarttime(System.nanoTime());

        try {
            result = pjp.proceed();
        } catch (Throwable throwable) {
            this.throwable = throwable;
        }

        rt.setNanoEndtime(System.nanoTime());
        rt.setExecutionResult(throwable == null ? RuntimePerformance.ExecutionResult.OK : RuntimePerformance.ExecutionResult.EXCEPTION);

        rt.setNode(NodeFactory.getNodeFactory().getNode(jpo));

        return rt;
    }

    public Object getResult() {
        return result;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
