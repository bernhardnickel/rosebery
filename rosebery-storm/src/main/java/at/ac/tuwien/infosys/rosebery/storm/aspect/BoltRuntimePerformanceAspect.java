package at.ac.tuwien.infosys.rosebery.storm.aspect;

import at.ac.tuwien.infosys.rosebery.common.aspect.RuntimePerformanceMeter;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.tuple.Tuple;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public class BoltRuntimePerformanceAspect {

    @Pointcut("execution(* backtype.storm.topology.IBasicBolt.execute(backtype.storm.tuple.Tuple, ..)) && this(bolt) && args(tuple, *)")
    public void boltExecute(IBasicBolt bolt, Tuple tuple) {}

    @Around("boltExecute(bolt, tuple)")
    public Object aroundBoltExecute(ProceedingJoinPoint pjp, IBasicBolt bolt, Tuple tuple) throws Throwable {

        RuntimePerformanceMeter meter = new RuntimePerformanceMeter();

        String sequence = null;

        if (tuple instanceof SequencedTupleAspect.SequencedTuple) {
            sequence = ((SequencedTupleAspect.SequencedTuple)tuple).getSequence();
        }

        meter.meter(pjp, bolt, sequence);

        RuntimePerformance rt = meter.getRuntimePerformance();

        PublicationService.getPublicationService().publish(rt);


        if (meter.getThrowable() != null) {
            throw meter.getThrowable();
        }

        return meter.getResult();
    }
}
