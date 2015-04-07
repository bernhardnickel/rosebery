package at.ac.tuwien.infosys.rosebery.common.factory.node;

import at.ac.tuwien.infosys.rosebery.common.model.Node;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class SystemPropertyNodeFactory implements NodeFactory {
    private static final String SYSTEM_PROPERTY_PREFIX = "rosebery";
    private static final String NODE_ID_SYSTEM_PROPERTY = SYSTEM_PROPERTY_PREFIX + ".nodeId";
    private static final String NODE_PURPOSE_SYSTEM_PROPERTY = SYSTEM_PROPERTY_PREFIX + ".nodePurpose";

    @Override
    public Node getNode() {
        Node node = new Node();
        node.setNodeId(System.getProperty(NODE_ID_SYSTEM_PROPERTY));
        node.setNodePurpose(System.getProperty(NODE_PURPOSE_SYSTEM_PROPERTY));

        return node;
    }

    @Override
    public Node getNode(String pointcutId) {
        return getNode();
    }
}
