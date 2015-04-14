package at.ac.tuwien.infosys.rosebery.storm.aspect;

import at.ac.tuwien.infosys.rosebery.common.aspect.sequence.SequencePassOnAspect;
import backtype.storm.tuple.Tuple;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public class BoltSequencePassOnAspect extends SequencePassOnAspect {
    @Pointcut("execution(*backtype.storm.tuple.TupleImpl.new(..))")
    public void newTuple() {}

    @Pointcut("execution(* backtype.storm.topology.IBasicBolt.execute(backtype.storm.tuple.Tuple, ..)) && args(in, *)")
    public void boltExecute(Tuple in) {}

    @Pointcut("execution(* backtype.storm.topology.IBasicOutputCollector.emit(..))")
    public void outCollectorEmit() {}

    @Override
    @Pointcut("cflow(boltExecute(in)) && cflow(outCollectorEmit()) && newTuple() && this(out)")
    public void finished(Object in, Object out) {}

    @Override
    @After("finished(in, out)")
    public void assignSequence(Object in, Object out) {
        super.assignSequence(in, out);
    }
}
