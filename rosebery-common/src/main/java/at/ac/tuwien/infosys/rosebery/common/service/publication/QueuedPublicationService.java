package at.ac.tuwien.infosys.rosebery.common.service.publication;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class QueuedPublicationService implements PublicationService {

    private final BlockingQueue<Measurement> queue = new LinkedBlockingQueue<>();

    public QueuedPublicationService(PublicationService delegate) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Measurement measurement = queue.take();
                        delegate.publish(measurement);
                    } catch (InterruptedException e) {}
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public <T extends Measurement> void publish(T t) {
        try {
            queue.put(t);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
