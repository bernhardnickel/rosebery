package at.ac.tuwien.infosys.rosebery.common.service.publication;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class ClassPathScanPublicationService implements PublicationService {
    Set<PublicationService> publicationServices = new HashSet<>();

    public ClassPathScanPublicationService() {
        Reflections reflections = new Reflections();
        Set<Class<? extends PublicationService>> pubServices =  reflections.getSubTypesOf(PublicationService.class);

        for (Class<? extends PublicationService> clazz : pubServices) {
            if (!clazz.equals(ClassPathScanPublicationService.class) && !Modifier.isAbstract(clazz.getModifiers())) {
                publicationServices.add(DefaultPublicationService.newInstance(clazz));
            }
        }
    }

    @Override
    public <T extends Measurement> void publish(T t) {
        for (PublicationService publicationService : publicationServices) {
            publicationService.publish(t);
        }
    }
}
