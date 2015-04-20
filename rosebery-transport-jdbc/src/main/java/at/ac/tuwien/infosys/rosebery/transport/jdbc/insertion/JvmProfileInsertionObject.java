package at.ac.tuwien.infosys.rosebery.transport.jdbc.insertion;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.JvmProfile;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import at.ac.tuwien.infosys.rosebery.transport.jdbc.dao.NodeDataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class JvmProfileInsertionObject implements MeasurementInsertionObject<JvmProfile> {
    private static final String SQL = "INSERT INTO jvm_profile(node_id, nanotime, process_cpu_time, process_cpu_load_max, process_cpu_load_avg, process_cpu_load_min, system_cpu_load_max, system_cpu_load_avg, system_cpu_load_min, heap_max, heap_usage_max, heap_usage_avg, heap_usage_min) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";


    private Connection connection;
    private NodeDataAccessObject nodeDao;

    public JvmProfileInsertionObject(Connection connection) {
        this.connection = connection;
        this.nodeDao = new NodeDataAccessObject(connection);
    }

    @Override
    public Long insert(JvmProfile jvmProfile) {
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setLong(1, nodeDao.getNodeId(jvmProfile.getNode()));
            pst.setLong(2, jvmProfile.getNanoTime());

            pst.setLong(3, jvmProfile.getProcessCpuTime());
            pst.setDouble(4, jvmProfile.getProcessCpuLoadMax());
            pst.setDouble(5, jvmProfile.getProcessCpuLoadAvg());
            pst.setDouble(6, jvmProfile.getProcessCpuLoadMin());
            pst.setDouble(7, jvmProfile.getSystemCpuLoadMax());
            pst.setDouble(8, jvmProfile.getSystemCpuLoadAvg());
            pst.setDouble(9, jvmProfile.getSystemCpuLoadMin());
            pst.setLong(10, jvmProfile.getHeapMax());
            pst.setDouble(11, jvmProfile.getHeapUsageMax());
            pst.setDouble(12, jvmProfile.getHeapUsageAvg());
            pst.setDouble(13, jvmProfile.getHeapUsageMin());

            if (pst.executeUpdate() != 1) {
                throw new SQLException("Error inserting runtime performance");
            }
            ResultSet rs = pst.getGeneratedKeys();

            try {
                if (!rs.next()) {
                    throw new SQLException("No key generated");
                }
                return rs.getLong(1);
            } finally {
                 rs.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    //tjo
                }
            }
        }

    }
}
