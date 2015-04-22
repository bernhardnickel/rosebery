package at.ac.tuwien.infosys.rosebery.common.factory.node;

import at.ac.tuwien.infosys.rosebery.common.model.Node;

/**
 * Node factory interface
 * Gets the node for a object or running application
 *
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public interface NodeFactory {


    /**
     * Get node for the running application
     * @return
     */
    public Node getNode();

    /**
     * Get node for a certain id
     * @param id
     * @return
     */
    public Node getNode(String id);

    /**
     * Get node for an object
     * Typical implementation: Use class name of the object as id
     * and call getNode(id)
     *
     * @param jpo
     * @return
     */
    public Node getNode(Object jpo);

    public static NodeFactory getNodeFactory() {
        return DefaultNodeFactory.getInstance();
    }
}
