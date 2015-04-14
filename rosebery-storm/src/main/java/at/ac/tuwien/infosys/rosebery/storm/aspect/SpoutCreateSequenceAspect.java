package at.ac.tuwien.infosys.rosebery.storm.aspect;

import at.ac.tuwien.infosys.rosebery.common.aspect.sequence.CreateSequenceAspect;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public class SpoutCreateSequenceAspect extends CreateSequenceAspect {
    @Pointcut("execution(*backtype.storm.tuple.TupleImpl.new(..))")
    public void newTuple() {}

    @Pointcut("execution(* backtype.storm.spout.ISpout.nextTuple(..))")
    public void spoutNextTuple() {}

    @Pointcut("execution(* backtype.storm.spout.ISpoutOutputCollector.emit(..))")
    public void spoutCollectorEmit() {}

    @Override
    @Pointcut("cflow(spoutNextTuple()) && cflow(spoutCollectorEmit()) && newTuple() && this(o)")
    public void created(Object o) {}

    @Override
    @After("created(o)")
    public void createSequence(Object o) {
        super.createSequence(o);
    }
}
