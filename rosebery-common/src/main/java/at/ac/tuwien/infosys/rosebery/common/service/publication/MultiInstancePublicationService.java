package at.ac.tuwien.infosys.rosebery.common.service.publication;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;

import java.util.ArrayList;
import java.util.List;

/**
 * Publication service that calls it's child publication services
 * one after another
 *
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class MultiInstancePublicationService implements PublicationService {

    private List<PublicationService> instances = new ArrayList<>();

    public void addPublicationService(PublicationService instance) {
        instances.add(instance);
    }

    @Override
    public <T extends Measurement> void publish(T t) {
        for (PublicationService instance : instances) {
            instance.publish(t);
        }
    }
}
