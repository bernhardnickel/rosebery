package at.ac.tuwien.infosys.rosebery.transport.file.csv;

import at.ac.tuwien.infosys.rosebery.common.model.Node;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;
import at.ac.tuwien.infosys.rosebery.transport.file.AbstractFileTransportService;
import at.ac.tuwien.infosys.rosebery.transport.file.csv.serializer.RuntimePerformanceCsvSerializer;
import at.ac.tuwien.infosys.rosebery.transport.file.csv.serializer.Serializer;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class CsvFileTransportService extends AbstractFileTransportService {

    private Map<Class<? extends Measurement>, Serializer<? extends Measurement>> map = new HashMap<Class<? extends Measurement>, Serializer<? extends Measurement>> ();

    private static final String POSTFIX = ".csv";

    private static final String DIR_SYSTEM_PROPERTY="rosebery.csvTransportDirectory";

    private String path;

    public CsvFileTransportService() {
        path = System.getProperty(DIR_SYSTEM_PROPERTY);

        map.put(RuntimePerformance.class, new RuntimePerformanceCsvSerializer());
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
