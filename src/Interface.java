import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.lang.System.exit;

/**
 * Institution: University of Newcastle
 * Programmer:  Ben Sutter
 * Course Code: COMP2230
 * UID: 3063467
 * Assignment 1
 * Interface Class
 * Interface.java
 * Last Modified: 31/10/2016
 */
public class Interface {
    private String input;
    private boolean running;
    private ArrayList<Hotspot> hotspots;
    private Scanner console = new Scanner(System.in);

    public static double INTER_CLUSTER_DISTANCE = 0.0;

    public Interface(String input) {
        this.input = input;
        this.running = true;
        this.hotspots = new ArrayList<>();
    }

    /**
     * run()
     * Main Program
     */
    public void run() {
        // Read input file
        try {
            String path = "./out/production/c3063467A1/" + input;
            input = readFile(path, StandardCharsets.UTF_8);
            // Setup regex
            String regex = "(?<ID>[\\d]+)[\\s]+(?<XCOORD>[\\d]+)[\\s]+(?<YCOORD>[\\d]+)[\\r\\n]*";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);

            // Read in hotspots
            int i = 0;
            while(matcher.find()) {
                hotspots.add(new Hotspot(
                        Integer.parseInt(matcher.group("ID")),
                        i,
                        Integer.parseInt(matcher.group("XCOORD")),
                        Integer.parseInt(matcher.group("YCOORD"))
                ));
                i++;
            }
            // Run the menu until close
            System.out.println("Hello and welcome to Kruskal’s Clustering! \n");
            System.out.println("There are " + hotspots.size() + " hotspots. \n");
            while(running) {
                program();
            }
        }
        catch (Exception ex){
            System.out.println(ex.toString());
            exit(1);
        }
    }

    /**
     * program()
     * Secondary program - what is run if the user wishes to continue
     */
    private void program() {
        // Run initial prompts
        System.out.println("How many emergency stations would you like? \n" +
                "(Enter a number between 1 and 5 to place the emergency stations." +
                "Enter 0 to exit.)\n");
        // Get input from console
        String input = console.next();
        // Pull input int
        int inputInt = parseInput(input);
        while (inputInt > 5 || inputInt < 0) {
            System.out.println("Invalid input, please enter a number between 1 and 5, or 0 to exit.");
            inputInt = parseInput(console.next());
        }
        // Ok validation is passed - now check for program end
        if (inputInt == 0) {
            this.running = false;
            System.out.println("Thanks for using Kruskal's Clustering!");
            System.out.println("Bye :) \n");
            return;
        }

        // We want to continue - lets do it
        // To start, we need to get a list of edges from our vertices
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i < hotspots.size(); i++) {
            for (int j = i + 1; j < hotspots.size(); j++) {
                edges.add(new Edge(hotspots.get(i), hotspots.get(j)));
            }
        }
        // Now use kruskals algorithm on the edges to find clusters
        KruskalsCluster algorithm = new KruskalsCluster(edges, hotspots.size(), inputInt);
        int[] clusterResult = algorithm.runAlgorithm();
        // Need to cluster hotspots based off parent array
        // So first make an array to store each cluster
        ArrayList<ArrayList<Hotspot>> clusters = new ArrayList<>();
        // Now get the roots and use a mapping array to reference later
        int k = 0;
        int[] mapping = new int[hotspots.size()];
        for (int i = 0; i < hotspots.size(); i++) {
            if (clusterResult[i] == i) {
                // This is a root
                clusters.add(k, new ArrayList<>());
                clusters.get(k).add(hotspots.get(i));
                mapping[i] = k;
                k++;
            }
        }
        // Go through all hotspots and use mapping array to put them in the right cluster
        for (int i = 0; i < hotspots.size(); i++) {
            // We have already added the roots, so skip them
            if (clusterResult[i] != i) {
                // Add the hotspot to the correct cluster
                clusters.get(mapping[clusterResult[i]]).add(hotspots.get(i));
            }
        }
        // How we have hotspots clustered we can report
        int i = 1;
        for(ArrayList<Hotspot> c : clusters) {
            Collections.sort(c);
            double x = 0, y = 0;
            StringJoiner out = new StringJoiner(",", "{", "}");
            for (Hotspot h : c) {
                x += h.getX();
                y += h.getY();
                out.add(Integer.toString(h.getId()));
            }
            System.out.println("Station " + i);
            System.out.println("Coordinates: " + this.calcCoords(x, y, c.size()));
            System.out.println("Hotspots: " + out.toString() + "\n");
            i++;
        }
        NumberFormat formatter = new DecimalFormat("#0.00");
        System.out.println("Inter-cluster Distance: " + formatter.format(inputInt == 1 ? 0.00 : INTER_CLUSTER_DISTANCE) + "\n");
    }

    /**
     * Private helper function to read in file
     * @param path
     * @param encoding
     * @return
     * @throws IOException
     */
    private static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    /**
     * Private helper function to parse input string
     * @param input
     * @return
     */
    private int parseInput(String input) {
        Pattern p = Pattern.compile("\\-*\\d+");
        Matcher m = p.matcher(input);
        while (m.find()) {
            return Integer.parseInt(m.group());
        }
        return -1;
    }

    /**
     * Private helper function to calc average coords for station
     * @param x
     * @param y
     * @param size
     * @return
     */
    private String calcCoords(double x, double y, int size) {
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        return "(" + numberFormat.format(x/size) + "," + numberFormat.format(y/size) + ")";
    }
}
