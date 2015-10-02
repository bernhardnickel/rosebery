package at.ac.tuwien.infosys.rosebery.publication.jmx.test;

import at.ac.tuwien.infosys.rosebery.common.model.Node;
import at.ac.tuwien.infosys.rosebery.common.model.measurement.RuntimePerformance;
import at.ac.tuwien.infosys.rosebery.publication.jmx.MeasurementNotificationService;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class TestRunner {
    public static void main(String... args) throws Exception {
        MeasurementNotificationService service = new MeasurementNotificationService();

        while (true) {
            RuntimePerformance t = new RuntimePerformance();
            t.setNode(new Node());
            t.getNode().setNodeId("nodeId");
            t.getNode().setNodePurpose("nodePurpose");
            t.setNanoStarttime(System.nanoTime());
            t.setNanoEndtime(System.nanoTime() + 10);
            t.setExecutionResult(RuntimePerformance.ExecutionResult.OK);

            System.out.println(t);
            service.publish(t);

            Thread.sleep(1000l);
        }
    }
}
