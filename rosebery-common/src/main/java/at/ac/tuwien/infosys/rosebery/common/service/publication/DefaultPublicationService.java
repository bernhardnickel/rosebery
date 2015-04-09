package at.ac.tuwien.infosys.rosebery.common.service.publication;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;
import org.reflections.Reflections;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class DefaultPublicationService implements PublicationService {
    private static final String PUB_SERVICE_SYSTEM_PROPERTY = "rosebery.publicationService";

    private static PublicationService instance = null;

    Set<PublicationService> publicationServices = null;

    private DefaultPublicationService() {

    }

    public static PublicationService getInstance() {
        if (instance == null) {
            instance = new DefaultPublicationService();
        }

        return instance;
    }

    @Override
    public <T extends Measurement> void publish(T t) {
        if (publicationServices == null) {
            initPublicationServices();
        }

        for (PublicationService publicationService : publicationServices) {
            publicationService.publish(t);
        }
    }

    private void initPublicationServices() {
        publicationServices = new HashSet<>();

        String className = System.getProperty(PUB_SERVICE_SYSTEM_PROPERTY);

        if ("ALL".equals(className)) {
            Reflections reflections = new Reflections();
            Set<Class<? extends PublicationService>> pubServices =  reflections.getSubTypesOf(PublicationService.class);

            for (Class<? extends PublicationService> clazz : pubServices) {
                if (!clazz.equals(DefaultPublicationService.class)) {
                    publicationServices.add(newInstance(clazz));
                }
            }
        }

        if(className != null) {
            try {
                publicationServices.add(newInstance((Class<? extends PublicationService>)Class.forName(className)));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            return;
        }

    }

    private PublicationService newInstance(Class<? extends PublicationService> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
