package at.ac.tuwien.infosys.rosebery.storm.aspect;

import backtype.storm.tuple.Tuple;
import org.apache.thrift7.TUnion;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public class SerializationAspect {
    @Pointcut("execution(* backtype.storm.serialization.ITupleSerializer.serialize(backtype.storm.tuple.Tuple)) && args(tuple)")
    public void serialize(Tuple tuple) {}

    /*
    @Pointcut("execution(* backtype.storm.serialization.ITupleDeserializer.deserialize(..))")
    public void deserialize () {}
    */


    @Before("serialize(tuple)")
    public void before(Tuple tuple) {
        System.out.println("BEFORE TUPLE SERIALIZATION!!!!");
    }
}
