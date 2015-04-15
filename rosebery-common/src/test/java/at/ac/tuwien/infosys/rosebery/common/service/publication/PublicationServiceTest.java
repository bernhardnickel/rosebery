package at.ac.tuwien.infosys.rosebery.common.service.publication;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import at.ac.tuwien.infosys.rosebery.common.service.publication.concurrent.FireAndForgetPublicationService;
import at.ac.tuwien.infosys.rosebery.common.service.publication.concurrent.ThreadPoolPublicationService;
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
        assertTrue(publicationService instanceof DefaultPublicationService);

        publicationService.publish(new RuntimePerformance());
    }

    @Test
    public void testInitialisation2() {
        System.setProperty("rosebery.publicationService", "at.ac.tuwien.infosys.rosebery.common.service.publication.DefaultPublicationService");
        System.setProperty("rosebery.publicationMode", "FIREFORGET");

        PublicationService publicationService = PublicationService.getPublicationService();


        assertNotNull(publicationService);
        assertTrue(publicationService instanceof FireAndForgetPublicationService);

        publicationService.publish(new RuntimePerformance());
    }
    @Test
    public void testInitialisation3() {
        System.setProperty("rosebery.publicationService", "at.ac.tuwien.infosys.rosebery.common.service.publication.DefaultPublicationService");
        System.setProperty("rosebery.publicationMode", "QUEUE");

        PublicationService publicationService = PublicationService.getPublicationService();


        assertNotNull(publicationService);
        assertTrue(publicationService instanceof QueuedPublicationService);

        publicationService.publish(new RuntimePerformance());
    }

    @Test
    public void testInitialisation4() {
        System.setProperty("rosebery.publicationService", "at.ac.tuwien.infosys.rosebery.common.service.publication.DefaultPublicationService");
        System.setProperty("rosebery.publicationMode", "THREADPOOL");

        PublicationService publicationService = PublicationService.getPublicationService();


        assertNotNull(publicationService);
        assertTrue(publicationService instanceof ThreadPoolPublicationService);

        publicationService.publish(new RuntimePerformance());
    }

}
