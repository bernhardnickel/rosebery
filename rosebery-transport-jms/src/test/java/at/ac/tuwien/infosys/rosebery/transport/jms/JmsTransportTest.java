package at.ac.tuwien.infosys.rosebery.transport.jms;

import at.ac.tuwien.infosys.rosebery.common.model.Node;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.jms.*;
import javax.naming.InitialContext;

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
        rt.setNanoStarttime(0);
        rt.setNanoEndtime(10);
        rt.setSequence("asdf");

        service.publish(rt);

    }

    @Test
    public void testTopic() throws Exception {
        System.setProperty("rosebery.jmsDestinationResource", "MeasurementTopic");

        PublicationService service = new JmsPublicationService();

        RuntimePerformance rt = new RuntimePerformance();
        rt.setNode(new Node());
        rt.setNanoStarttime(0);
        rt.setNanoEndtime(10);
        rt.setSequence("asdf");

        service.publish(rt);

        Thread.sleep(1000);

    }
}
