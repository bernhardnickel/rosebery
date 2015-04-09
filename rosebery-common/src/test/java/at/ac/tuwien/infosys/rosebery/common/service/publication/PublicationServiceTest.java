package at.ac.tuwien.infosys.rosebery.common.service.publication;

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
    }

    @Test
    public void testInitialisation2() {
        System.setProperty("rosebery.publicationService", "ALL");

        PublicationService publicationService = PublicationService.getPublicationService();


        assertNotNull(publicationService);
        assertEquals(ClassPathScanPublicationService.class, publicationService.getClass());
    }
}
