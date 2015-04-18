package at.ac.tuwien.infosys.rosebery.transport.jdbc;

import at.ac.tuwien.infosys.rosebery.common.model.measurement.JvmProfile;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.Measurement;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.profiling.ExecutionProfile;
import at.ac.tuwien.infosys.rosebery.common.service.publication.PublicationService;
import at.ac.tuwien.infosys.rosebery.transport.jdbc.ds.PropertyDataSource;
import at.ac.tuwien.infosys.rosebery.transport.jdbc.insertion.ExecutionProfileInsertionObject;
import at.ac.tuwien.infosys.rosebery.transport.jdbc.insertion.JvmProfileInsertionObject;
import at.ac.tuwien.infosys.rosebery.transport.jdbc.insertion.MeasurementInsertionObject;
import at.ac.tuwien.infosys.rosebery.transport.jdbc.insertion.RuntimePerformanceInsertionObject;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class JdbcPublicationService implements PublicationService {
    private static final String JDBC_CONFIG_SYSTEM_PROPERTY = "rosbery.jdbcConfiguration";

    private DataSource dataSource;
    private Map<Class<? extends Measurement>, MeasurementInsertionObject<? extends Measurement>> insertionObjectMap = new HashMap<>();


    public JdbcPublicationService() {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(System.getProperty(JDBC_CONFIG_SYSTEM_PROPERTY)));
        } catch (IOException e) {
            throw new RuntimeException();
        }

        dataSource = new PropertyDataSource(properties);

        insertionObjectMap.put(RuntimePerformance.class, new RuntimePerformanceInsertionObject(getConnection()));
        insertionObjectMap.put(ExecutionProfile.class, new ExecutionProfileInsertionObject(getConnection()));
        insertionObjectMap.put(JvmProfile.class, new JvmProfileInsertionObject(getConnection()));
    }

    public JdbcPublicationService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public <T extends Measurement> void publish(T t) {
        MeasurementInsertionObject io = insertionObjectMap.get(t.getClass());

        io.insert(t);
    }

    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
