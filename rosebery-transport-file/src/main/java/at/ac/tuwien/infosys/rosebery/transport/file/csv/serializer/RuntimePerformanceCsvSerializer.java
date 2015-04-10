package at.ac.tuwien.infosys.rosebery.transport.file.csv.serializer;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import at.ac.tuwien.infosys.rosebery.transport.file.Serializer;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class RuntimePerformanceCsvSerializer implements Serializer<RuntimePerformance> {
    @Override
    public String serialize(RuntimePerformance runtimePerformance) {
        StringBuilder str = new StringBuilder();

        str.append(runtimePerformance.getNode().getNodeId()).append(';');
        str.append(runtimePerformance.getNode().getNodePurpose()).append(';');
        str.append(runtimePerformance.getSequence()).append(';');
        str.append(runtimePerformance.getNanoStarttime()).append(';');
        str.append(runtimePerformance.getNanoEndtime()).append(";");
        str.append(runtimePerformance.getDuration()).append(";");
        str.append(runtimePerformance.getExecutionResult());


        return str.toString();
    }
}
