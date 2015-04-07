package at.ac.tuwien.infosys.rosebery.common.service.publication;

import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class DefaultPublicationService implements PublicationService {
    private static final String PUB_SERVICE_SYSTEM_PROPERTY = "rosebery.publicationService";

    Set<PublicationService> publicationServices = null;

    @Override
    public <T extends Serializable> void publish(T t) {
        if (publicationServices == null) {
            initPublicationServices();
        }

        for (PublicationService publicationService : publicationServices) {
            publicationService.publish(t);
        }
    }

    private void initPublicationServices() {
        publicationServices =  new HashSet<>();

        String className = System.getProperty(PUB_SERVICE_SYSTEM_PROPERTY);

        if(className != null) {
            try {
                publicationServices.add(newInstance((Class<? extends PublicationService>)Class.forName(className)));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        Reflections reflections = new Reflections();
        Set<Class<? extends PublicationService>> pubServices =  reflections.getSubTypesOf(PublicationService.class);

        for (Class<? extends PublicationService> clazz : pubServices) {
            if (!clazz.equals(DefaultPublicationService.class)) {
                publicationServices.add(newInstance(clazz));
            }
        }
    }

    private PublicationService newInstance(Class<? extends PublicationService> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String... args) {
        DefaultPublicationService ps = new DefaultPublicationService();
        ps.publish(new Long(1));
    }
}
