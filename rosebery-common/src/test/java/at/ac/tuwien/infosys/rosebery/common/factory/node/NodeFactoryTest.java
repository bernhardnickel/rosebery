package at.ac.tuwien.infosys.rosebery.common.factory.node;

import at.ac.tuwien.infosys.rosebery.common.model.Node;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class NodeFactoryTest {

    @Before
    public void before() {
        DefaultNodeFactory.instance = null;
    }

    @Test
    public void testNodeFactoryInit() {
        NodeFactory nodeFactory = NodeFactory.getNodeFactory();
        assertNotNull(nodeFactory);
        assertTrue(nodeFactory instanceof DefaultNodeFactory);
    }

    @Test
    public void testDefaultNodeFactory() {
        System.setProperty("rosebery.nodeId", "myNodeId");
        System.setProperty("rosebery.nodePurpose", "myNodePurpose");

        NodeFactory nodeFactory = NodeFactory.getNodeFactory();
        assertNotNull(nodeFactory);
        assertTrue(nodeFactory instanceof DefaultNodeFactory);

        Node node = nodeFactory.getNode();

        assertNotNull(node);
        assertEquals("myNodeId", node.getNodeId());
        assertEquals("myNodePurpose", node.getNodePurpose());
    }
}
