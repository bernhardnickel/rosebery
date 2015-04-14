package at.ac.tuwien.infosys.rosebery.common.service.publication.concurrent;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;
import at.ac.tuwien.infosys.rosebery.common.service.publication.concurrent.PublicationRunnable;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class FireAndForgetPublicationService implements PublicationService {
    private PublicationService delegate;

    public FireAndForgetPublicationService(PublicationService delegate) {
        this.delegate = delegate;
    }

    @Override
    public <T extends Measurement> void publish(T t) {
        new Thread(new PublicationRunnable(delegate, t)).start();
    }
}
