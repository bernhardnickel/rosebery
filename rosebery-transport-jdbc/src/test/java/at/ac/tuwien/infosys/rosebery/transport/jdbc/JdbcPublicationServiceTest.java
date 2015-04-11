package at.ac.tuwien.infosys.rosebery.transport.jdbc;

import at.ac.tuwien.infosys.rosebery.common.model.Node;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.profiling.ExecutionProfile;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.profiling.ResourceSnapshot;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static org.junit.Assert.*;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class JdbcPublicationServiceTest {

    private static final String URL = "jdbc:h2:./h2data/db";

    @BeforeClass
    public static void beforeClass() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("resources/schema-h2.sql"));

        List<String> creates = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            if (line.trim().startsWith("CREATE TABLE")) {
                String create = str.toString().trim();
                if (!create.equals("")) {
                    creates.add(create);
                }
                str = new StringBuilder();
            }
            str.append(line);
        }

        //Final statement
        String create = str.toString().trim();
        if (!create.equals("")) {
            creates.add(create);
        }


        Connection con = DriverManager.getConnection(URL);

        for (String sql : creates) {
            Statement st = con.createStatement();
            st.execute(sql);
            st.close();
        }

        con.close();

        System.setProperty("rosbery.jdbcConfiguration", "src/test/resources/jdbc.properties");
    }

    @AfterClass
    public static void afterClass() {
        File f = new File("h2data");

        if (f.exists()) {
            for (File sf : f.listFiles()) {
                sf.delete();
            }
            f.delete();
        }

    }

    @Test
    public void test() throws Exception {
        JdbcPublicationService service = new JdbcPublicationService();

        ExecutionProfile ep = new ExecutionProfile();
        ep.setNode(new Node());
        ep.getNode().setNodeId("nodeId");
        ep.getNode().setNodePurpose("nodePurpose");

        ep.setNanoStarttime(0l);
        ep.setNanoEndtime(10l);
        ep.setExecutionResult(RuntimePerformance.ExecutionResult.OK);

        ep.setSnapshots(new TreeSet<>());

        ResourceSnapshot snapshot = new ResourceSnapshot();
        snapshot.setNanoTime(0l);
        snapshot.setSystemCpuLoad(1d);
        snapshot.setProcessCpuLoad(0.5d);
        snapshot.setProcessCpuTime(2l);
        snapshot.setThreadCpuTime(1l);
        snapshot.setHeapUsage(5l);
        snapshot.setHeapMax(5l);

        ep.getSnapshots().add(snapshot);

        service.publish(ep);

        Connection con = DriverManager.getConnection(URL);

        PreparedStatement pst = con.prepareStatement("SELECT * FROM node");
        ResultSet rs = pst.executeQuery();

        assertTrue(rs.next());

        assertEquals(1l, rs.getLong(1));
        assertEquals("nodeId", rs.getString(2));
        assertEquals("nodePurpose", rs.getString(3));

        assertFalse(rs.next());

        rs.close();
        pst.close();

        pst = con.prepareStatement("SELECT * FROM runtime_performance");
        rs = pst.executeQuery();

        assertRow(rs, 1l, 1l, null, 0l, 10l, 10l, "OK");

        assertFalse(rs.next());
        rs.close();
        pst.close();

        pst = con.prepareStatement("SELECT * FROM execution_profile");
        rs = pst.executeQuery();

        assertTrue(rs.next());
        assertEquals(1l, rs.getLong(1));

        assertFalse(rs.next());
        rs.close();
        pst.close();

        pst = con.prepareStatement("SELECT * FROM resource_snapshot");
        rs = pst.executeQuery();

        assertTrue(rs.next());
        assertEquals(1l, rs.getLong(1));
        assertEquals(0l, rs.getLong(2));
        assertEquals(new Double(1d), new Double(rs.getDouble(3)));
        assertEquals(new Double(0.5d), new Double(rs.getDouble(4)));
        assertEquals(2l, rs.getLong(5));
        assertEquals(1l, rs.getLong(6));
        assertEquals(5l, rs.getLong(7));
        assertEquals(5l, rs.getLong(8));


        assertFalse(rs.next());
        rs.close();
        pst.close();

        con.close();
    }

    private void assertRow(ResultSet rs,long id, long nodeId, String seq, long starttime, long endtime, long duration, String result) throws SQLException {
        assertTrue(rs.next());
        assertEquals(id, rs.getLong(1));
        assertEquals(nodeId, rs.getLong(2));
        assertEquals(seq, rs.getString(3));
        assertEquals(starttime, rs.getLong(4));
        assertEquals(endtime, rs.getLong(5));
        assertEquals(duration, rs.getLong(6));
        assertEquals(result, rs.getString(7));
    }
}
