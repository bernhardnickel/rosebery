package at.ac.tuwien.infosys.rosebery.common.service.publication;

import java.io.Serializable;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public interface PublicationService {
    public <T extends Serializable> void publish(T t);
}
