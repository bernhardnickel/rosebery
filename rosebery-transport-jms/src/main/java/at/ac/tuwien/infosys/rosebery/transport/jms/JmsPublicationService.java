package at.ac.tuwien.infosys.rosebery.transport.jms;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class JmsPublicationService implements PublicationService {
    private static final String CONNECTION_FACTORY_RESOURCE_SYSTEM_PROPERTY = "rosebery.jmsConnectionFactoryResource";
    private static final String DESTINATION_RESOURCE_SYSTEM_PROPERTY = "rosebery.jmsDestinationResource";

    private Destination destination;
    private Connection connection;


    public JmsPublicationService() {
        try {
            Context context = new InitialContext();

            ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup(System.getProperty(CONNECTION_FACTORY_RESOURCE_SYSTEM_PROPERTY));
            connection  = connectionFactory.createConnection();
            destination = (Destination) context.lookup(System.getProperty(DESTINATION_RESOURCE_SYSTEM_PROPERTY));
        } catch(NamingException | JMSException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T extends Measurement> void publish(T t) {
        try {
            sendMessage(t);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

    }

    private <T extends Measurement> void sendMessage(T t) throws JMSException{
        Session session = connection.createSession(true, 0);
        MessageProducer producer = session.createProducer(destination);
        producer.send(destination, session.createObjectMessage(t));
        session.commit();
        producer.close();
        session.close();
    }
}
