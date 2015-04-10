package at.ac.tuwien.infosys.rosebery.transport.jdbc;

import at.ac.tuwien.infosys.rosebery.common.model.Node;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        System.setProperty("rosbery.jdbcConfiguration", "src/test/resources/jdbc.properties");

        JdbcPublicationService service = new JdbcPublicationService();

        RuntimePerformance rt = new RuntimePerformance();
        rt.setNode(new Node());
        rt.getNode().setNodeId("nodeId");
        rt.getNode().setNodePurpose("nodePurpose");

        rt.setNanoStarttime(0l);
        rt.setNanoEndtime(10l);
        rt.setExecutionResult(RuntimePerformance.ExecutionResult.OK);

        service.publish(rt);

        rt.setSequence("t1234");
        rt.setNanoStarttime(10l);
        rt.setNanoEndtime(20l);

        service.publish(rt);

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

        assertRow(rs, 1l, null, 0l, 10l, 10l, "OK");
        assertRow(rs, 1l, "t1234", 10l, 20l, 10l, "OK");

        assertFalse(rs.next());

        rs.close();

        con.close();
    }

    private void assertRow(ResultSet rs, long nodeId, String seq, long starttime, long endtime, long duration, String result) throws SQLException {
        assertTrue(rs.next());
        assertEquals(nodeId, rs.getLong(1));
        assertEquals(seq, rs.getString(2));
        assertEquals(starttime, rs.getLong(3));
        assertEquals(endtime, rs.getLong(4));
        assertEquals(duration, rs.getLong(5));
        assertEquals(result, rs.getString(6));
    }
}
