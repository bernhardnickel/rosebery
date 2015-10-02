package at.ac.tuwien.infosys.rosebery.publication.file;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public interface Serializer<T extends Measurement> {
    public String serialize(T  t);
}
