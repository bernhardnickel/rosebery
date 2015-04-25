package at.ac.tuwien.infosys.rosebery.common.aspect;

import at.ac.tuwien.infosys.rosebery.common.factory.node.NodeFactory;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Meter class for measuring runtime perforamce
 *
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class RuntimePerformanceMeter {

    private RuntimePerformance runtimePerformance;
    private Object result;
    private Throwable throwable;

    public RuntimePerformanceMeter() {
    }

    public RuntimePerformanceMeter(RuntimePerformance runtimePerformance) {
        this.runtimePerformance = runtimePerformance;
    }

    public void meter(ProceedingJoinPoint pjp, Object jpo) {
        meter(pjp, jpo, null);
    }

    /**
     * Method to measure runtime performance of a method
     *
     *
     * @param pjp
     * @param jpo
     * @param sequence
     */
    public void meter(ProceedingJoinPoint pjp, Object jpo, String sequence) {
        if (runtimePerformance == null) {
            runtimePerformance = new RuntimePerformance();
        }


        runtimePerformance.setStarttime(System.currentTimeMillis());
        runtimePerformance.setSequence(sequence);

        long nanoStart = System.nanoTime();

        try {
            result = proceed(pjp);
        } catch (Throwable throwable) {
            this.throwable = throwable;
        }

        // Set duration in nanotime
        runtimePerformance.setDuration(System.nanoTime() - nanoStart);

        runtimePerformance.setEndtime(System.currentTimeMillis());
        runtimePerformance.setExecutionResult(throwable == null ? RuntimePerformance.ExecutionResult.OK : RuntimePerformance.ExecutionResult.EXCEPTION);

        runtimePerformance.setNode(NodeFactory.getNodeFactory().getNode(jpo));
    }

    protected Object proceed(ProceedingJoinPoint pjp) throws Throwable {
        return pjp.proceed();
    }

    public Object getResult() {
        return result;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public RuntimePerformance getRuntimePerformance() {
        return runtimePerformance;
    }
}
