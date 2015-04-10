package at.ac.tuwien.infosys.rosebery.transport.jdbc.insertion;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import at.ac.tuwien.infosys.rosebery.transport.jdbc.dao.NodeDataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class RuntimePerformanceInsertionObject implements MeasurementInsertionObject<RuntimePerformance> {
    private static final String SQL = "INSERT INTO runtime_performance(node_id, seq, starttime, endtime, duration, result) VALUES (?,?,?,?,?,?)";


    private Connection connection;
    private NodeDataAccessObject nodeDao;

    public RuntimePerformanceInsertionObject(Connection connection) {
        this.connection = connection;
        this.nodeDao = new NodeDataAccessObject(connection);
    }

    @Override
    public void insert(RuntimePerformance runtimePerformance) {
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(SQL);
            pst.setLong(1, nodeDao.getNodeId(runtimePerformance.getNode()));
            pst.setString(2, runtimePerformance.getSequence());
            pst.setLong(3, runtimePerformance.getNanoStarttime());
            pst.setLong(4, runtimePerformance.getNanoEndtime());
            pst.setLong(5, runtimePerformance.getDuration());
            pst.setString(6, runtimePerformance.getExecutionResult().name());

            if (pst.executeUpdate() != 1) {
                throw new SQLException("Error inserting runtime performance");
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
