package at.ac.tuwien.infosys.rosebery.common.factory.node;

import at.ac.tuwien.infosys.rosebery.common.model.Node;

/**
 * Default node factory that either reads nodeId and nodePurpose from system properties
 * or initializes an alternative node factory class
 *
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
            synchronized (DefaultNodeFactory.class) {
                if (instance != null) {
                    return instance;
                }

                // Get node factory system property
                String factoryClass = System.getProperty(NODE_FACTORY_SYSTEM_PROPERTY);

                // If an alternative node factory class is set, set the instance to the alternative node factory
                // Otherwise use the default implementation
                if (factoryClass == null) {
                    instance = new DefaultNodeFactory();
                } else {
                    instance = newInstance(factoryClass);
                }
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
    public Node getNode(String id) {
        return getNode();
    }

    @Override
    public Node getNode(Object jpo) {
        return getNode();
    }
}
