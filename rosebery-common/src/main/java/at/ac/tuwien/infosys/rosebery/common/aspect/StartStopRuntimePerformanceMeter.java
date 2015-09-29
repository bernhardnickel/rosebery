package at.ac.tuwien.infosys.rosebery.common.aspect;

import at.ac.tuwien.infosys.rosebery.common.factory.node.NodeFactory;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Meter class for measuring runtime perforamce
 *
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class StartStopRuntimePerformanceMeter {

    private RuntimePerformance runtimePerformance;
    private Object result;
    private Throwable throwable;

    private long nanoStart;

    public StartStopRuntimePerformanceMeter() {
    }

    public StartStopRuntimePerformanceMeter(RuntimePerformance runtimePerformance) {
        this.runtimePerformance = runtimePerformance;
    }


    public void start(Object jpo) {
        start(jpo, null);
    }

    public void start(Object jpo, String sequence) {
        if (runtimePerformance == null) {
            runtimePerformance = new RuntimePerformance();
        }


        runtimePerformance.setStarttime(System.currentTimeMillis());
        runtimePerformance.setSequence(sequence);
        runtimePerformance.setNode(NodeFactory.getNodeFactory().getNode(jpo));

        nanoStart = System.nanoTime();



        runtimePerformance.setExecutionResult(throwable == null ? RuntimePerformance.ExecutionResult.OK : RuntimePerformance.ExecutionResult.EXCEPTION);


    }

    public RuntimePerformance stop(Throwable throwable) {
        stop();
        this.throwable = throwable;
        runtimePerformance.setExecutionResult(RuntimePerformance.ExecutionResult.EXCEPTION);
        return runtimePerformance;
    }

    public RuntimePerformance stop(Object result) {
        stop();
        this.result = result;
        runtimePerformance.setExecutionResult(RuntimePerformance.ExecutionResult.OK);
        return runtimePerformance;
    }

    private void stop() {
        // Set duration in nanotime
        runtimePerformance.setDuration(System.nanoTime() - nanoStart);

        runtimePerformance.setEndtime(System.currentTimeMillis());
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
