package at.ac.tuwien.infosys.rosebery.common.service.publication.concurrent;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;
import at.ac.tuwien.infosys.rosebery.common.service.publication.concurrent.PublicationRunnable;

/**
 * Fire and forget publication service
 * Creates a new thread that publishes the measurement asynchronously
 *
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class ThreadPerTaskPublicationService implements PublicationService {
    private PublicationService delegate;

    public ThreadPerTaskPublicationService(PublicationService delegate) {
        this.delegate = delegate;
    }

    @Override
    public <T extends Measurement> void publish(T t) {
        new Thread(new PublicationRunnable(delegate, t)).start();
    }
}
