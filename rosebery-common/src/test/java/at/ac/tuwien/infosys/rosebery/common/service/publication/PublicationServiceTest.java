package at.ac.tuwien.infosys.rosebery.common.service.publication;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class PublicationServiceTest {

    @Before
    public void before() {
        DefaultPublicationService.instance = null;
    }

    @Test
    public void testInitialisation1() {
        PublicationService publicationService = PublicationService.getPublicationService();


        assertNotNull(publicationService);
        assertTrue(publicationService instanceof QueuedPublicationService);

        publicationService.publish(new RuntimePerformance());
    }

}
