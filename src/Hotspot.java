/**
 * Institution: University of Newcastle
 * Programmer:  Ben Sutter
 * Course Code: COMP2230
 * UID: 3063467
 * Assignment 1
 * Hotspot Class
 * Hotspot.java
 * Models a Hotspot
 * Last Modified: 31/10/2016
 */
public class Hotspot implements Comparable<Hotspot> {
    private int id;
    private int incrementalId;
    private double x;
    private double y;

    public Hotspot(int id, int incrementalId, double x, double y) {
        this.id = id;
        this.incrementalId = incrementalId;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getIncrementalId() {
        return incrementalId;
    }

    @Override
    public int compareTo(Hotspot o) {
        if (this.id < o.id) {
            return -1;
        } else if (this.id > o.id) {
            return 1;
        } else {
            return 0;
        }
    }
}
