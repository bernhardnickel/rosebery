package at.ac.tuwien.infosys.rosebery.publication.jms;

import at.ac.tuwien.infosys.rosebery.common.model.Node;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class JmsTransportTest {

    @BeforeClass
    public static void beforeClass() throws Exception{

        System.setProperty("rosebery.jmsConnectionFactoryResource", "connectionFactory");

    }

    @Test
    public void testQueue() throws Exception {
        System.setProperty("rosebery.jmsDestinationResource", "MeasurementQueue");

        PublicationService service = new JmsPublicationService();

        RuntimePerformance rt = new RuntimePerformance();
        rt.setNode(new Node());
        rt.setSequence("asdf");

        service.publish(rt);

    }

    @Test
    public void testTopic() throws Exception {
        System.setProperty("rosebery.jmsDestinationResource", "MeasurementTopic");

        PublicationService service = new JmsPublicationService();

        RuntimePerformance rt = new RuntimePerformance();
        rt.setNode(new Node());
        rt.setSequence("asdf");

        service.publish(rt);

        Thread.sleep(1000);

    }
}
