package at.ac.tuwien.infosys.rosebery.transport.file;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;
import at.ac.tuwien.infosys.rosebery.transport.file.csv.serializer.Serializer;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public abstract class AbstractFileTransportService implements PublicationService {

    private Map<Class<? extends Measurement>, PrintStream> streams = new HashMap<>();

    protected <T extends Measurement> void doPublish(T t) throws IOException {
        Class<? extends Measurement> clazz = t.getClass();
        PrintStream stream = getPrintStream(clazz);
        stream.println(getSerializer(clazz).serialize(t));
    }

    private PrintStream getPrintStream(Class<? extends Measurement> clazz) throws IOException {
        PrintStream stream = streams.get(clazz);

        if (stream == null) {
            Path path = FileSystems.getDefault().getPath(getPath(clazz));

            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            stream = new PrintStream(Files.newOutputStream(path, StandardOpenOption.APPEND));
            streams.put(clazz, stream);
        }

        return stream;
    }

    @Override
    public <T extends Measurement> void publish(T t) {
        try {
            doPublish(t);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract String getPath(Class<? extends Measurement> clazz);

    public abstract <T extends Measurement> Serializer<T> getSerializer(Class<? extends Measurement> clazz);
}
