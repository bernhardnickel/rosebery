package at.ac.tuwien.infosys.rosebery.transport.jmx;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class MeasurementNotificationSender extends NotificationBroadcasterSupport implements PublicationService, MeasurementNotificationSenderMBean {
    private static final String OBJECT_NAME = "at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService:type=Measurement";

    private static final String NOTIFICATION_TYPE = "Measurement Notification";

    private static final String MESSAGE_PREFIX = "Measurement notification with measurement type: ";

    private long sequenceNumber = 0l;

    public MeasurementNotificationSender() {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = null;
        try {
            objectName = new ObjectName(OBJECT_NAME);
            server.registerMBean(this, objectName);
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T extends Measurement> void publish(T t) {
        Notification n = new Notification(NOTIFICATION_TYPE, MeasurementNotificationSender.class.getName(), sequenceNumber++, MESSAGE_PREFIX + t.getClass().getName());
        n.setUserData(t);
        sendNotification(n);
    }
}
