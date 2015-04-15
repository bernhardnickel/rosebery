package at.ac.tuwien.infosys.rosebery.common.service.publication;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;
import at.ac.tuwien.infosys.rosebery.common.service.publication.concurrent.FireAndForgetPublicationService;
import at.ac.tuwien.infosys.rosebery.common.service.publication.concurrent.ThreadPoolPublicationService;

import java.util.Enumeration;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class DefaultPublicationService implements PublicationService {
    private static final String PUBLICATION_SERVICE_SYSTEM_PROPERTY = "rosebery.publicationService";
    private static final String PUBLICATION_MODE_SYSTEM_PROPERTY = "rosebery.publicationMode";

    private enum PublicationMode {
        FIREFORGET,
        QUEUE,
        THREADPOOL
    }

    protected static PublicationService instance = null;

    private DefaultPublicationService() {
    }

    public static PublicationService getInstance() {

        if (instance == null) {
            synchronized (DefaultPublicationService.class) {
                if (instance != null) {
                    return instance;
                }

                String serviceClass = System.getProperty(PUBLICATION_SERVICE_SYSTEM_PROPERTY);

                if (serviceClass == null) {
                    instance = new DefaultPublicationService();
                } else if (serviceClass.contains(":")) {
                    MultiInstancePublicationService mInstance = new MultiInstancePublicationService();

                    for (String iServiceClass : serviceClass.split(":")) {
                        mInstance.addPublicationService(newInstance(iServiceClass));
                    }

                    instance = mInstance;
                } else {
                    instance = newInstance(serviceClass);
                }

                String publicationMode = System.getProperty(PUBLICATION_MODE_SYSTEM_PROPERTY);

                if (PublicationMode.FIREFORGET.name().equals(publicationMode)) {
                    instance = new FireAndForgetPublicationService(instance);
                } else if (PublicationMode.QUEUE.name().equals(publicationMode)) {
                    instance = new QueuedPublicationService(instance);
                } else if (PublicationMode.THREADPOOL.name().equals(publicationMode)) {
                    instance = new ThreadPoolPublicationService(instance, 1, 10);
                }
            }

        }

        return instance;
    }

    @Override
    public <T extends Measurement> void publish(T t) {
        System.out.println(t);
    }

    protected static PublicationService newInstance(Class<? extends PublicationService> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected static PublicationService newInstance(String className) {
        Class<? extends PublicationService> clazz = null;

        try {
             clazz = (Class<? extends PublicationService>)Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return newInstance(clazz);
    }
}
