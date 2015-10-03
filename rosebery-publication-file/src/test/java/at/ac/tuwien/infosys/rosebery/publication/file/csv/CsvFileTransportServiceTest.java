package at.ac.tuwien.infosys.rosebery.publication.file.csv;

import at.ac.tuwien.infosys.rosebery.common.model.Node;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import org.junit.Test;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;
/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class CsvFileTransportServiceTest {

    @Test
    public void test() throws Exception {
        System.setProperty("rosebery.csvTransportDirectory", "src/test/resources");

        Path path = FileSystems.getDefault().getPath("src/test/resources/" + RuntimePerformance.class.getName() + ".csv");

        Files.deleteIfExists(path);

        CsvFilePublicationService service = new CsvFilePublicationService();

        RuntimePerformance rt = new RuntimePerformance();
        rt.setNode(new Node());
        rt.getNode().setNodeId("nodeId");
        rt.getNode().setNodePurpose("nodePurpose");
        rt.setNanoStarttime(0l);
        rt.setNanoEndtime(10l);
        rt.setExecutionResult(RuntimePerformance.ExecutionResult.OK);

        service.publish(rt);


        assertTrue(Files.exists(path));
        assertEquals(1, Files.readAllLines(path).size());
        assertEquals("nodeId;nodePurpose;null;0;10;10;OK", Files.readAllLines(path).get(0));
    }

    @Test
    public void test2() throws Exception {
        System.setProperty("rosebery.csvTransportDirectory", "src/test/resources");

        Path path = FileSystems.getDefault().getPath("src/test/resources/" + RuntimePerformance.class.getName() + ".csv");

        Files.deleteIfExists(path);

        CsvFilePublicationService service = new CsvFilePublicationService();

        RuntimePerformance rt = new RuntimePerformance();
        rt.setNode(new Node());
        rt.getNode().setNodeId("nodeId");
        rt.getNode().setNodePurpose("nodePurpose");
        rt.setNanoStarttime(0l);
        rt.setNanoEndtime(10l);
        rt.setExecutionResult(RuntimePerformance.ExecutionResult.OK);

        service.publish(rt);

        rt.setNanoStarttime(10l);
        rt.setNanoEndtime(20l);

        service.publish(rt);


        assertTrue(Files.exists(path));
        assertEquals(2, Files.readAllLines(path).size());
        assertEquals("nodeId;nodePurpose;null;0;10;10;OK", Files.readAllLines(path).get(0));
        assertEquals("nodeId;nodePurpose;null;10;20;10;OK", Files.readAllLines(path).get(1));
    }
}
