package at.ac.tuwien.infosys.rosebery.transport.file.csv;

import at.ac.tuwien.infosys.rosebery.common.model.Node;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import at.ac.tuwien.infosys.rosebery.transport.file.AbstractFileTransportService;
import at.ac.tuwien.infosys.rosebery.transport.file.csv.serializer.RuntimePerformanceCsvSerializer;
import at.ac.tuwien.infosys.rosebery.transport.file.csv.serializer.Serializer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class CsvFileTransportService extends AbstractFileTransportService {
    private Map<Class<? extends Measurement>, Serializer<? extends Measurement>> map = new HashMap<Class<? extends Measurement>, Serializer<? extends Measurement>> ();

    public CsvFileTransportService() {
        map.put(RuntimePerformance.class, new RuntimePerformanceCsvSerializer());
    }

    @Override
    public <T extends Measurement> Serializer<T> getSerializer(Class<? extends Measurement> clazz) {
        return (Serializer<T>) map.get(clazz);
    }
}
