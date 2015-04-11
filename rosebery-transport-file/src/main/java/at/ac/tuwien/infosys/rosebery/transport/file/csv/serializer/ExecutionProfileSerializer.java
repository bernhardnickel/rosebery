package at.ac.tuwien.infosys.rosebery.transport.file.csv.serializer;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.profiling.ExecutionProfile;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.profiling.ResourceSnapshot;
import at.ac.tuwien.infosys.rosebery.transport.file.Serializer;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class ExecutionProfileSerializer implements Serializer<ExecutionProfile> {

    RuntimePerformanceCsvSerializer rtpSerializer = new RuntimePerformanceCsvSerializer();

    @Override
    public String serialize(ExecutionProfile executionProfile) {
        StringBuilder str = new StringBuilder();
        str.append(rtpSerializer.serialize(executionProfile));
        str.append(System.lineSeparator());

        if (executionProfile.getSnapshots() != null) {
            for (ResourceSnapshot resourceSnapshot : executionProfile.getSnapshots()) {
                str.append(resourceSnapshot.getNanoTime()).append(";");
                str.append(resourceSnapshot.getSystemCpuLoad()).append(";");
                str.append(resourceSnapshot.getProcessCpuLoad()).append(";");
                str.append(resourceSnapshot.getProcessCpuTime()).append(";");
                str.append(resourceSnapshot.getThreadCpuTime()).append(";");
                str.append(resourceSnapshot.getHeapMax()).append(";");
                str.append(resourceSnapshot.getHeapUsage()).append(System.lineSeparator());
            }

        }
        return str.toString().trim();
    }
}
