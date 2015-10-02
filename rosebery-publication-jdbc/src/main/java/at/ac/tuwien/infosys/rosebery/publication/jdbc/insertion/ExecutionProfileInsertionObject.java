package at.ac.tuwien.infosys.rosebery.publication.jdbc.insertion;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.profiling.ExecutionProfile;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.profiling.ResourceSnapshot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class ExecutionProfileInsertionObject implements MeasurementInsertionObject<ExecutionProfile> {

    private static final String EP_INSERT_SQL = "INSERT INTO execution_profile (rtp_id) VALUES(?)";
    private static final String RS_INSERT_SQL = "INSERT INTO resource_snapshot (ep_id, timestamp, system_cpu_load, process_cpu_load, process_cpu_time, thread_cpu_time, heap_max, heap_usage) VALUES (?,?,?,?,?,?,?,?)";

    private Connection connection;
    private RuntimePerformanceInsertionObject rtpInsertionObject;

    public ExecutionProfileInsertionObject(Connection connection) {
        this.connection = connection;
        this.rtpInsertionObject = new RuntimePerformanceInsertionObject(connection);
    }

    @Override
    public Long insert(ExecutionProfile executionProfile) {
        Long id = rtpInsertionObject.insert(executionProfile);

         try {
             insertExecutionProfile(id, executionProfile);

             if (executionProfile.getSnapshots() != null) {
                 for (ResourceSnapshot snapshot : executionProfile.getSnapshots()) {
                     insertResourceSnapshot(id, snapshot);
                 }
             }
         }catch (SQLException e) {
             throw new RuntimeException(e);
         }

        return id;
    }

    private void insertExecutionProfile(Long id, ExecutionProfile executionProfile) throws SQLException {
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(EP_INSERT_SQL);
            pst.setLong(1, id);

            if (pst.executeUpdate() != 1) {
                throw new SQLException("Error inserting execution profile");
            }
        } finally {
            if (pst != null) {
                pst.close();
            }
        }
    }

    private void insertResourceSnapshot(Long epId, ResourceSnapshot snapshot) throws SQLException {
        PreparedStatement pst = null;

        try {
            pst = connection.prepareStatement(RS_INSERT_SQL);

            pst.setLong(1, epId);
            pst.setLong(2, snapshot.getTimestamp());
            pst.setDouble(3, snapshot.getSystemCpuLoad());
            pst.setDouble(4, snapshot.getProcessCpuLoad());
            pst.setLong(5, snapshot.getProcessCpuTime());
            pst.setLong(6, snapshot.getThreadCpuTime());
            pst.setLong(7, snapshot.getHeapMax());
            pst.setLong(8, snapshot.getHeapUsage());

            if (pst.executeUpdate() != 1) {
                throw new SQLException("Error inserting resource snapshot");
            }
        } finally {
            if (pst == null) {
                pst.close();
            }
        }
    }
}
