package at.ac.tuwien.infosys.rosebery.publication.jdbc;

import at.ac.tuwien.infosys.rosebery.common.model.Node;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.JvmProfile;
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
public class PostgresSchemaTest {

    private static final String URL = "jdbc:postgresql://localhost/rosebery";

    @Test
    public void test() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("resources/schema-postgres.sql"));

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


        Connection con = DriverManager.getConnection(URL, "rosebery", "");

        for (String sql : creates) {
            Statement st = con.createStatement();
            st.execute(sql);
            st.close();
        }

        con.close();
    }

}
