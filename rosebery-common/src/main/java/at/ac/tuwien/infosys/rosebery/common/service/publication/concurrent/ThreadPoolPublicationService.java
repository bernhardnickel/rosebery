package at.ac.tuwien.infosys.rosebery.common.service.publication.concurrent;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Thread pool publication service
 * Uses a thread pool to publish measurements asynchronously
 *
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class ThreadPoolPublicationService implements PublicationService {
    private PublicationService delegate;

    private ThreadPoolExecutor executor;

    public ThreadPoolPublicationService(PublicationService delegate, int corePoolSize, int maxPoolSize) {
        this.delegate = delegate;
        executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    @Override
    public <T extends Measurement> void publish(T t) {
        executor.execute(new PublicationRunnable(delegate, t));
    }
}
