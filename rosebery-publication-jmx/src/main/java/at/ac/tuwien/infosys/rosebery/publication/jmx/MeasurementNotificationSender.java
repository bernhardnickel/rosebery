package at.ac.tuwien.infosys.rosebery.publication.jmx;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class MeasurementNotificationSender extends NotificationBroadcasterSupport implements MeasurementNotificationSenderMBean {
    private static final String OBJECT_NAME = "at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService:type=";

    private static final String NOTIFICATION_TYPE = "Measurement Notification";

    private static final String MESSAGE_PREFIX = "Measurement notification with measurement type: ";

    private long sequenceNumber = 0l;

    private String message;

    public MeasurementNotificationSender(Class<? extends Measurement> clazz) {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = null;
        message = MESSAGE_PREFIX + clazz.getName();

        try {
            objectName = new ObjectName(OBJECT_NAME +  clazz.getSimpleName());
            server.registerMBean(this, objectName);
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException e) {
            throw new RuntimeException(e);
        }
    }


    public <T extends Measurement> void sendNotification(T t) {
        Notification n = new Notification(NOTIFICATION_TYPE, MeasurementNotificationSender.class.getName(), sequenceNumber++, message);
        n.setUserData(t);
        sendNotification(n);
    }
}
