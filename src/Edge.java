/**
 * Institution: University of Newcastle
 * Programmer:  Ben Sutter
 * Course Code: COMP2230
 * UID: 3063467
 * Assignment 1
 * Edge Class
 * Edge.java
 * Models an edge of the graph
 * Last Modified: 31/10/2016
 */
public class Edge implements Comparable<Edge> {
    private Hotspot v1;
    private Hotspot v2;
    private double weight;

    public Edge(Hotspot v1, Hotspot v2) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = Math.sqrt(Math.pow(v2.getX() - v1.getX(), 2) + Math.pow(v2.getY() - v1.getY(),2));
    }

    @Override
    public int compareTo(Edge o) {
        if (this.weight < o.weight) {
            return -1;
        } else if (this.weight > o.weight) {
            return 1;
        } else {
            return 0;
        }
    }

    public double getWeight() {
        return weight;
    }

    public Hotspot getV2() {
        return v2;
    }

    public Hotspot getV1() {
        return v1;
    }
}
