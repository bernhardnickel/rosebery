package at.ac.tuwien.infosys.rosebery.transport.log4j;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;
import org.apache.log4j.Logger;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class Log4jPublicationService implements PublicationService {
    private static final Logger logger = Logger.getLogger(PublicationService.class);

    @Override
    public <T extends Measurement> void publish(T t) {
        if (logger.isInfoEnabled()) {
            logger.info("Published measurement " + t);
        }
    }
}
