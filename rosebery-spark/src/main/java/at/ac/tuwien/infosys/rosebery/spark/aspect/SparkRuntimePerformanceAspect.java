package at.ac.tuwien.infosys.rosebery.spark.aspect;

import at.ac.tuwien.infosys.rosebery.common.aspect.RuntimePerformanceMeter;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.UUID;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public class SparkRuntimePerformanceAspect {

    @Pointcut("execution(* org.apache.spark.api.java.function.Function.call(..))")
    public void functionCall() {}

    @Pointcut("execution (* org.apache.spark.streaming.receiver.Receiver.store(..))")
    public void receiver() {}

    /*
    @Pointcut("execution(* org.apache.spark.streaming.api.java.JavaDStream.map(..))")
    public void streamMap() {}

    @Pointcut("execution(* org.apache.spark.streaming.api.java.JavaDStream.transform(..))")
    public void streamTransform() {}

    @Pointcut("(cflow(streamMap()) || cflow(streamTransform())) && functionCall()")
    public void scope() {}
*/
    @Around("receiver()")
    public Object aroundReceiver(ProceedingJoinPoint pjp) throws Throwable {
        Object arg = pjp.getArgs()[0];

        arg = new SequencedObject(UUID.randomUUID().toString(), arg);

        return pjp.proceed(new Object[] {arg});
    }

    @Around("functionCall() && this(jpo)")
    public Object around(ProceedingJoinPoint pjp, Object jpo) throws Throwable {
        RuntimePerformanceMeter meter = new SequencingRuntimePerformanceMeter();

        String sequence = null;
        if (pjp.getArgs()[0] instanceof SequencedObject) {
            sequence = ((SequencedObject)pjp.getArgs()[0]).getSequence();
        }

        RuntimePerformance rt = meter.meter(pjp, jpo, sequence);

        PublicationService.getPublicationService().publish(rt);

        if(meter.getResult() != null) {
            return meter.getResult();
        }

        if (meter.getThrowable() != null) {
            throw meter.getThrowable();
        }

        return null;
    }
}
