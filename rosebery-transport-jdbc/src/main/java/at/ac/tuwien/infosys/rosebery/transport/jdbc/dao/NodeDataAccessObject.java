package at.ac.tuwien.infosys.rosebery.transport.jdbc.dao;

import at.ac.tuwien.infosys.rosebery.common.model.Node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class NodeDataAccessObject {
    private static final String SELECT = "SELECT id FROM node WHERE node_id = ? and node_purpose = ?";
    private static final String INSERT = "INSERT INTO node (node_id, node_purpose) VALUES (?,?)";


    private Map<Node, Long> idCache = new HashMap<>();
    private Connection connection;


    public NodeDataAccessObject(Connection connection) {
        this.connection = connection;
    }

    public Long getNodeId(Node node) {
        Long id = idCache.get(node);

        if (id == null) {
            try {
                id = checkInsertAndCacheId(node);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return id;
    }

    private Long checkInsertAndCacheId(Node node) throws SQLException {
        Long id = selectId(node);

        if (id == null) {
            insert(node);
            id = selectId(node);
        }

        idCache.put(node, id);

        return id;
    }

    private Long selectId(Node node) throws SQLException {
        Long result = null;
        PreparedStatement pst = connection.prepareStatement(SELECT);

        pst.setString(1, node.getNodeId());
        pst.setString(2, node.getNodePurpose());

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            result = rs.getLong(1);
        }

        rs.close();
        pst.close();

        return result;
    }

    private void insert(Node node)  throws SQLException {
        PreparedStatement pst = connection.prepareStatement(INSERT);

        pst.setString(1, node.getNodeId());
        pst.setString(2, node.getNodePurpose());

        try {
            if (pst.executeUpdate() != 1) {
                throw new SQLException("Error inserting node");
            }
        } finally {
            pst.close();
        }
    }
}
