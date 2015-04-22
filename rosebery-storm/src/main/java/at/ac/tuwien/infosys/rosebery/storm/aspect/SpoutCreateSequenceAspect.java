package at.ac.tuwien.infosys.rosebery.storm.aspect;

import at.ac.tuwien.infosys.rosebery.common.aspect.RuntimePerformanceMeter;
import at.ac.tuwien.infosys.rosebery.common.aspect.sequence.CreateSequenceAspect;
import at.ac.tuwien.infosys.rosebery.common.aspect.sequence.SequencedObject;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclarePrecedence;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public class SpoutCreateSequenceAspect extends CreateSequenceAspect {
    @Pointcut("execution(*backtype.storm.tuple.TupleImpl.new(..)) && this(o)")
    public void created(Object o) {}

    @Pointcut("execution(* backtype.storm.spout.ISpout.nextTuple(..))")
    public void spoutNextTuple() {}

    @Pointcut("execution(* backtype.storm.spout.ISpoutOutputCollector.emit(..))")
    public void spoutCollectorEmit() {}

    @Pointcut("execution(* at.ac.tuwien.infosys.rosebery.common.aspect.RuntimePerformanceMeter.meter(..)) && this(meter)")
    public void meter(RuntimePerformanceMeter meter) {}

    @Pointcut("cflow(meter(meter)) && cflow(spoutNextTuple()) && cflow(spoutCollectorEmit()) && created(o)")
    public void createdInFlow(RuntimePerformanceMeter meter, Object o) {}

    @After("createdInFlow(meter, o)")
    public void createSequence(RuntimePerformanceMeter meter, Object o) {
        super.createSequence(o);

        if (o instanceof SequencedObject) {
            meter.getRuntimePerformance().setSequence(((SequencedObject)o).getSequence());
        }
    }
}
