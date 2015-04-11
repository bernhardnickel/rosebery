package at.ac.tuwien.infosys.rosebery.common.service.publication;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class DefaultPublicationService implements PublicationService {
    private static final String PUBLICATION_SERVICE_SYSTEM_PROPERTY = "rosebery.publicationService";

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
