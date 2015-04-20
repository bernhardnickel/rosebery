package at.ac.tuwien.infosys.rosebery.jvm.profiling.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
@Aspect
public abstract class LoadProfilingAspect {
    @Pointcut
    public abstract void scope();

    @Before("scope()")
    public void beforeScope() {
        try {
            Class.forName("at.ac.tuwien.infosys.rosebery.jvm.profiling.ProfilingThread");
        } catch(ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
