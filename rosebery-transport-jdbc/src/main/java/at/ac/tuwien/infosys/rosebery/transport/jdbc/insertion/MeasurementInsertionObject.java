package at.ac.tuwien.infosys.rosebery.transport.jdbc.insertion;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;

import java.sql.Connection;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public interface MeasurementInsertionObject<T extends Measurement> {
    public Long insert(T t);
}
