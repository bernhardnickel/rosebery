package at.ac.tuwien.infosys.rosebery.test.model;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class Vertex {
    private double distance;
    private Node a,b;

    public Vertex(Node a, Node b, double distance) {
        this.distance = distance;
        this.a = a;
        this.b = b;
    }

    public double getDistance() {
        return distance;
    }

    public Node getA() {
        return a;
    }

    public Node getB() {
        return b;
    }
}
