package at.ac.tuwien.infosys.rosebery.common.factory.node;

import at.ac.tuwien.infosys.rosebery.common.model.Node;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class DefaultNodeFactory implements NodeFactory {
    private static final String SYSTEM_PROPERTY_PREFIX = "rosebery";
    private static final String NODE_FACTORY_SYSTEM_PROPERTY = SYSTEM_PROPERTY_PREFIX + ".nodeFactory";
    private static final String NODE_ID_SYSTEM_PROPERTY = SYSTEM_PROPERTY_PREFIX + ".nodeId";
    private static final String NODE_PURPOSE_SYSTEM_PROPERTY = SYSTEM_PROPERTY_PREFIX + ".nodePurpose";

    protected static NodeFactory instance = null;

    public static NodeFactory getInstance() {
        if (instance == null) {
            String factoryClass = System.getProperty(NODE_FACTORY_SYSTEM_PROPERTY);

            if (factoryClass == null) {
                instance = new DefaultNodeFactory();
            } else {
                instance = newInstance(factoryClass);
            }
        }

        return instance;
    }

    private static NodeFactory newInstance(String className) {
        try {
            Class<? extends NodeFactory> clazz = (Class<? extends NodeFactory>)Class.forName(className);
            return clazz.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private DefaultNodeFactory() {

    }

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
