package at.ac.tuwien.infosys.rosebery.storm.aspect;

import backtype.storm.topology.IBasicBolt;
import backtype.storm.tuple.Tuple;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareMixin;
import org.aspectj.lang.annotation.Pointcut;

import java.util.UUID;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public class SequencedTupleAspect {
    public interface SequencedTuple {
        public String getSequence();
        public void setSequence(String sequence);
    }

    public static class SequencedTupleImpl implements SequencedTuple {
        private String sequence;

        @Override
        public String getSequence() {
            return sequence;
        }

        @Override
        public void setSequence(String sequence) {
            this.sequence = sequence;
        }
    }

    @DeclareMixin("backtype.storm.tuple.TupleImpl")
    public static SequencedTuple createSequencedTuple() {
        return new SequencedTupleImpl();
    }

    @Pointcut("execution(*backtype.storm.tuple.TupleImpl.new(..))")
    public void newTuple() {}

    @Pointcut("execution(* backtype.storm.spout.ISpout.nextTuple(..))")
    public void spoutNextTuple() {}

    @Pointcut("execution(* backtype.storm.spout.ISpoutOutputCollector.emit(..))")
    public void spoutCollectorEmit() {}

    @Pointcut("cflow(spoutNextTuple()) && cflow(spoutCollectorEmit()) && newTuple() && this(out)")
    public void newOutTupleAfterSpout(Tuple out) {}

    @After("newOutTupleAfterSpout(out)")
    public void afterNewOutTupleAfterSpout(Tuple out) {
        if (out instanceof SequencedTupleAspect.SequencedTuple) {
            ((SequencedTupleAspect.SequencedTuple)out).setSequence(UUID.randomUUID().toString());
        }
    }

    @Pointcut("execution(* backtype.storm.topology.IBasicBolt.execute(backtype.storm.tuple.Tuple, ..)) && args(tuple, *)")
    public void boltExecute(Tuple tuple) {}

    @Pointcut("execution(* backtype.storm.topology.IBasicOutputCollector.emit(..))")
    public void outCollectorEmit() {}

    @Pointcut("cflow(boltExecute(in)) && cflow(outCollectorEmit()) && newTuple() && this(out)")
    public void newOutTupleAfterBolt(Tuple in, Tuple out) {}

    @After("newOutTupleAfterBolt(in, out)")
    public void afterNewOutTupleAfterBolt(Tuple in, Tuple out) {
        if (in instanceof SequencedTupleAspect.SequencedTuple && out instanceof SequencedTupleAspect.SequencedTuple) {
            ((SequencedTupleAspect.SequencedTuple)out).setSequence(((SequencedTupleAspect.SequencedTuple) in).getSequence());
        }
    }
}
