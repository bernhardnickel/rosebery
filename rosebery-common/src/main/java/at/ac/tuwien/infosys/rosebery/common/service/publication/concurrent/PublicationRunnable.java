package at.ac.tuwien.infosys.rosebery.common.service.publication.concurrent;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class PublicationRunnable implements Runnable {

    private PublicationService publicationService;
    private Measurement measurement;

    public PublicationRunnable(PublicationService publicationService, Measurement measurement) {
        this.publicationService = publicationService;
        this.measurement = measurement;
    }

    @Override
    public void run() {
        publicationService.publish(measurement);
    }
}
