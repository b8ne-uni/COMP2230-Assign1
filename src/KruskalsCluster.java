import java.util.Collections;
import java.util.List;

/**
 * Institution: University of Newcastle
 * Programmer:  Ben Sutter
 * Course Code: COMP2230
 * UID: 3063467
 * Assignment 1
 * KruskalsCluster Class
 * KruskalsCluster.java
 * Last Modified: 31/10/2016
 */
public class KruskalsCluster {
    private int[] parent;
    private int[] rank;
    private int vertices;
    private int clusters;
    private List<Edge> edges;

    public KruskalsCluster(List<Edge> edges, int hotspots, int clusters) {
        this.vertices = hotspots;
        this.parent = new int[vertices];
        this.rank = new int[vertices];
        this.edges = edges;
        this.clusters = clusters;
    }

    /**
     * runAlgorith()
     * Runs Kruskals Algorithm
     * @return
     */
    public int[] runAlgorithm() {
        // First sort the edge list
        Collections.sort(edges);
        for (int i = 0; i < vertices; i++) {
            makeSet(i);
        }
        int i = 0;
        int count = 0;
        int edgesToAdd = vertices - clusters;
        while(count < edgesToAdd) {
            if (findset(edges.get(i).getV1().getIncrementalId()) != findset(edges.get(i).getV2().getIncrementalId())) {
                union(edges.get(i).getV1().getIncrementalId(), edges.get(i).getV2().getIncrementalId());
                count++;
            }
            i++;
        }

        // Thats it for kruskals, but do this next bit to get Inter-clustering distance
        while (i < edges.size() && findset(edges.get(i).getV1().getIncrementalId()) == findset(edges.get(i).getV2().getIncrementalId())) {
            i++;
        }
        if (i != edges.size()) {
            Interface.INTER_CLUSTER_DISTANCE = edges.get(i).getWeight();
        }

        return parent;
    }

    /**
     * Disjoint Set - Makeset
     * @param i
     */
    private void makeSet(int i) {
        // Init
        parent[i] = i;
        rank[i] = 0;
    }

    /**
     * Disjoint Set - Findset
     * @param i
     */
    private int findset(int i) {
        int root = i;
        while (root != parent[root]) {
            root = parent[root];
        }
        int j = parent[i];
        while (j != root) {
            parent[i] = root;
            i = j;
            j = parent[i];
        }
        return root;
    }

    /**
     * Disjoint Set - Union
     * @param i
     */
    private void union(int i, int j) {
        merge(findset(i), findset(j));
    }

    /**
     * Disjoint Set - Merge
     * @param i
     */
    private void merge(int i, int j) {
        if (rank[i] < rank[j]) {
            parent[i] = j;
        } else if (rank[i] > rank[j]) {
            parent[j] = i;
        } else {
            parent[i] = j;
            rank[j] = rank[j] + 1;
        }
    }
}
