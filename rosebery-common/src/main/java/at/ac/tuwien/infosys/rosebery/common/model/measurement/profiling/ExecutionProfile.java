package at.ac.tuwien.infosys.rosebery.common.model.measurement.profiling;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class ExecutionProfile extends RuntimePerformance {

    private Set<ResourceSnapshot> snapshots;

    public Set<ResourceSnapshot> getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(Set<ResourceSnapshot> snapshots) {
        this.snapshots = snapshots;
    }
}
