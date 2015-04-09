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
import java.util.Map;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public abstract class AbstractFileTransportService implements PublicationService {

    private Path path;


    public void setFile(Path path) {
        this.path = path;
    }

    public void setFile(String path) {
        this.path = FileSystems.getDefault().getPath(path);
    }

    @Override
    public <T extends Measurement> void publish(T t) {
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            PrintStream out = new PrintStream(Files.newOutputStream(path, StandardOpenOption.APPEND));
            out.println(getSerializer(t.getClass()).serialize(t));
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public abstract <T extends Measurement> Serializer<T> getSerializer(Class<? extends Measurement> clazz);
}
