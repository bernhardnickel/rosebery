package at.ac.tuwien.infosys.rosebery.common.factory.node;

import at.ac.tuwien.infosys.rosebery.common.model.Node;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Node factory that reads node id and purpose of one or multiple nodes
 * from a property file
 *
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class PropertyFileNodeFactory implements NodeFactory {
    private static final String PROPERTY_FILE_SYSTEM_PROPERTY = "rosebery.nodeFactoryFile";

    private Properties p = new Properties();

    public PropertyFileNodeFactory() {
        try {
            p.load(new FileInputStream(System.getProperty(PROPERTY_FILE_SYSTEM_PROPERTY)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Node getNode() {
        Node node = new Node();
        node.setNodeId(p.getProperty("nodeId"));
        node.setNodePurpose(p.getProperty("nodePurpose"));

        return node;
    }

    @Override
    public Node getNode(String id) {
        if (id == null) {
            return getNode();
        }

        Node node = new Node();
        node.setNodeId(p.getProperty(id + ".nodeId"));
        node.setNodePurpose(p.getProperty(id + ".nodePurpose"));

        return node;
    }

    @Override
    public Node getNode(Object jpo) {
        if (jpo == null) {
            return getNode();
        }

        return getNode(jpo.getClass().getCanonicalName());
    }
}
