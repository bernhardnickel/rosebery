package at.ac.tuwien.infosys.rosebery.transport.file.csv.serializer;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.JvmProfile;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import at.ac.tuwien.infosys.rosebery.transport.file.Serializer;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class JvmProfileCsvSerializer implements Serializer<JvmProfile> {
    @Override
    public String serialize(JvmProfile jvmProfile) {
        StringBuilder str = new StringBuilder();

        str.append(jvmProfile.getNode().getNodeId()).append(';');
        str.append(jvmProfile.getNode().getNodePurpose()).append(';');
        str.append(jvmProfile.getTimestamp()).append(";");
        str.append(jvmProfile.getSystemCpuLoadMax()).append(";");
        str.append(jvmProfile.getSystemCpuLoadMin()).append(";");
        str.append(jvmProfile.getSystemCpuLoadAvg()).append(";");
        str.append(jvmProfile.getProcessCpuTime()).append(";");
        str.append(jvmProfile.getProcessCpuLoadMax()).append(";");
        str.append(jvmProfile.getProcessCpuLoadMin()).append(";");
        str.append(jvmProfile.getProcessCpuLoadAvg()).append(";");
        str.append(jvmProfile.getHeapMax()).append(";");
        str.append(jvmProfile.getHeapUsageMax()).append(";");
        str.append(jvmProfile.getHeapUsageMin()).append(";");
        str.append(jvmProfile.getHeapUsageAvg());


        return str.toString();
    }
}
