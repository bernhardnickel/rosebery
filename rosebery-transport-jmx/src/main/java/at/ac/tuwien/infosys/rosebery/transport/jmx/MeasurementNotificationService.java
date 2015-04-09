package at.ac.tuwien.infosys.rosebery.transport.jmx;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class MeasurementNotificationService implements PublicationService {
    private Map<Class<? extends Measurement>, MeasurementNotificationSender> notificationSenderMap = new HashMap<>();

    @Override
    public <T extends Measurement> void publish(T t) {
        Class<? extends Measurement> clazz = t.getClass();
        MeasurementNotificationSender sender = notificationSenderMap.get(clazz);

        if (sender == null) {
            synchronized (this) {
                sender = notificationSenderMap.get(clazz);
                if (sender == null) {
                    sender = new MeasurementNotificationSender(clazz);
                    notificationSenderMap.put(clazz, sender);
                }
            }
        }

        sender.sendNotification(t);

    }
}
