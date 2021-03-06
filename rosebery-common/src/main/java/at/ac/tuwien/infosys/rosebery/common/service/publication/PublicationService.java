package at.ac.tuwien.infosys.rosebery.common.service.publication;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;

/**
 *
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public interface PublicationService {
    public <T extends Measurement> void publish(T t);

    public static PublicationService getPublicationService() {
        return DefaultPublicationService.getInstance();
    }
}
