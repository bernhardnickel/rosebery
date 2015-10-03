package at.ac.tuwien.infosys.rosebery.publication.file.csv;

import at.ac.tuwien.infosys.rosebery.common.configuration.Configuration;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.JvmProfile;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.profiling.ExecutionProfile;
import at.ac.tuwien.infosys.rosebery.publication.file.Serializer;
import at.ac.tuwien.infosys.rosebery.publication.file.csv.serializer.JvmProfileCsvSerializer;
import at.ac.tuwien.infosys.rosebery.publication.file.AbstractFilePublicationService;
import at.ac.tuwien.infosys.rosebery.publication.file.csv.serializer.ExecutionProfileSerializer;
import at.ac.tuwien.infosys.rosebery.publication.file.csv.serializer.RuntimePerformanceCsvSerializer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class CsvFilePublicationService extends AbstractFilePublicationService {

    private Map<Class<? extends Measurement>, Serializer<? extends Measurement>> map = new HashMap<Class<? extends Measurement>, Serializer<? extends Measurement>> ();

    private static final String POSTFIX = ".csv";

    private static final String DIR_SYSTEM_PROPERTY="rosebery.csvTransportDirectory";

    private String path;

    public CsvFilePublicationService() {
        path = Configuration.getProperty(DIR_SYSTEM_PROPERTY);

        map.put(RuntimePerformance.class, new RuntimePerformanceCsvSerializer());
        map.put(ExecutionProfile.class, new ExecutionProfileSerializer());
        map.put(JvmProfile.class, new JvmProfileCsvSerializer());
    }

    @Override
    protected String getPath(Class<? extends Measurement> clazz) {
        return path + (path.endsWith(File.separator) ? "" : File.separator) + clazz.getName() + POSTFIX;
    }

    @Override
    public <T extends Measurement> Serializer<T> getSerializer(Class<? extends Measurement> clazz) {
        return (Serializer<T>) map.get(clazz);
    }
}
