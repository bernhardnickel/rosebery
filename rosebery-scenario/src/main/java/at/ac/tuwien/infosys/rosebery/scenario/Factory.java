package at.ac.tuwien.infosys.rosebery.scenario;

import java.io.Serializable;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public interface Factory<T> extends Serializable {
    public T create();
}
