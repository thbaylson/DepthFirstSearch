import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Stack;

class Graph {

    /** List of vertices in the graph where the index in corresponds to vertex number. */
    private ArrayList<Vertex> vertexList;

    /** List at each vertex corresponds to the vertices adjacent to vertex. */
    private ArrayList<ArrayList<Vertex>> adjList;

    /** Value of true represents an edge between two vertices. */
    private boolean[][] adjMatrix;

    /** A list of vertices in the order that they were visited. */
    private ArrayList<Vertex> discovered;

    /**
     * The Graph data type. Receives an int[] to build a vertex list, adjacency list,
     * and an adjacency matrix.
     * @param input The int[] that represents vertex relationships.
     * @throws IllegalArgumentException Thrown when it is determined that the input file
     * contains an odd number of vertices.
     */
    public Graph(int[] input) throws IllegalArgumentException {
        buildVertexList(input);
        buildAdjacencies(input);
        discovered = new ArrayList<>();
    }//End Graph

    /**
     * Initializes and populates the vertexList field with known vertex values.
     * @param input The int[] representing vertex relationships.
     */
    private void buildVertexList(int[] input){
        vertexList = new ArrayList<>();
        for(int i = 0; i < input.length; i++){
            Vertex temp = new Vertex(input[i]);
            if(! vertexList.contains(temp)){
                vertexList.add(temp);
            }//End if
        }//End for
        vertexList.sort(new SortById());
    }//End buildVertexList

    /**
     * Initializes and populates the adjList and adjMatrix fields.
     * @param input The int[] representing vertex relationships.
     * @throws IllegalArgumentException Thrown when it is determined that the input file
     * contains an odd number of vertices.
     */
    private void buildAdjacencies(int[] input) throws IllegalArgumentException {
        adjList = new ArrayList<>();
        adjMatrix = new boolean[vertexList.size()][vertexList.size()];

        ArrayList<Vertex> newVArray;
        boolean oddElements = true;

        // Initializes every ArrayList<Vertex> in the ArrayList adjList.
        for(int i = 0; i < vertexList.size(); i++){
            newVArray = new ArrayList<>();
            adjList.add(newVArray);
        }//End for

        // Build the adjacency list and adjacency matrix at once.
        for(int k = 0; k < input.length - 1; k++){
            if(k % 2 == 0) {
                oddElements = false;
                adjMatrix[input[k]][input[k + 1]] = true;
                adjList.get(input[k]).add(vertexList.get(input[k+1]));
            }else{
                oddElements = true;
            }//End if/else
        }//End for
        if(oddElements){
            throw new IllegalArgumentException();
        }//End if
    }//End buildAdjacencies

    /**
     * Entry point to run operations specified in the handout.
     */
    void go(){
        Scanner sc = new Scanner(System.in);
        boolean inputLoop = true;
        while(inputLoop){
            System.out.print("Please enter valid source and destination vertices >> ");
            try{
                int s = Integer.parseInt(sc.next());
                int d = Integer.parseInt(sc.next());
                if(s < 0 || d < 0 || s > adjMatrix.length-1 || d > adjMatrix.length-1){
                    throw new IllegalArgumentException();
                }else{
                    printGraphStats(s, d);
                    inputLoop = false;
                }//End if/else
            }catch(IllegalArgumentException iae){
                System.out.println("Error: Please enter a pair of positive integer values "+
                        "less than the number of vertices");
            }//End try/catch
        }//End while
    }//End go

    /**
     * Finds the source and destination vertices. On invalid input, asks the user to
     * enter new vertices.
     * @param s The id for the start vertex
     * @param d The id for an end vertex that may or may not have a path from s
     */
    private void findSourceDest(int s, int d){
        int lastDiscovered = discovered.get(discovered.size() - 1).getId();
        if(lastDiscovered == d){
            System.out.print("\n[DFS Path: " + s + ", " + lastDiscovered + "]: Vertex " + s);
            for(int k = 1; k < discovered.size(); k++){
                if(!discovered.get(k).getColor().equals("red")){
                    System.out.print(", Vertex " + discovered.get(k).getId());
                }//End if
            }//End for
        }else{
            System.out.print("\n[DFS Path]: No path found");
        }

    }//End findSourceDest

    /**
     * Performs a depth-first search from source to destination or until the search is exhausted.
     * @param s The id for the start vertex
     * @param d The id for an end vertex that may or may not have a path from s
     */
    private void depthFirstSearch(int s, int d){
        discovered = new ArrayList<>();
        Stack<Vertex> search = new Stack<>();
        Vertex peeked = vertexList.get(s);
        peeked.setColor("green");
        search.push(peeked);
        discovered.add(peeked);

        ArrayList<Vertex> neighbors;
        boolean foundEnd = false;
        Vertex finished;
        Vertex nextNeighbor;
        while(!search.empty() && !foundEnd){
            peeked = search.peek();
            neighbors = getNewNeighbors(peeked);
            if(!neighbors.isEmpty()){
                nextNeighbor = neighbors.get(0);
                nextNeighbor.setColor("green");
                search.push(nextNeighbor);
                discovered.add(nextNeighbor);
                if(search.peek().getId() == d){
                    foundEnd = true;
                }//End if
            }else{
                finished = search.pop();
                finished.setColor("red");
            }//End else/if
        }//End while

        //This will display all visited vertices
        System.out.print("[DFS Discovered Vertices: " + discovered.get(0).getId() +
                ", " + discovered.get(discovered.size()-1).getId() + "]: Vertex " +
                discovered.get(0).getId());
        for(int i = 1; i < discovered.size(); i++){
            System.out.print(", Vertex " + discovered.get(i).getId());
        }//End for
    }//End depthFirstSearch

    /**
     * Returns all unvisited adjacent vertices to a given vertex.
     * @param v A vertex for which to receive unvisited adjacent vertices.
     * @return An ArrayList of all unvisited adjacent vertices.
     */
    private ArrayList<Vertex> getNewNeighbors(Vertex v){
        ArrayList<Vertex> temp = new ArrayList<>();
        for(Vertex adj : adjList.get(v.getId())){
            if(adj.getColor().equals("white")){
                temp.add(adj);
            }//End if
        }//End for
        return temp;
    }//End getNewNeighbors

    /**
     * Performs the transitive closure algorithm (Warshall's Algorithm)
     */
    private void transitiveClosure(){
        System.out.println("\n[TC New Edges]: ");
        for(int i = 0; i < adjMatrix.length; i++){
            for(int j = 0; j < adjMatrix.length; j++){
                for(int k = 0; k < adjMatrix.length; k++){
                    if(adjMatrix[j][i] && adjMatrix[i][k]){
                        if(!adjList.get(j).contains(vertexList.get(k))){
                            System.out.println(vertexList.get(j).getId() +"\t" + k);
                            adjMatrix[j][k] = true;
                            adjList.get(j).add(vertexList.get(k));
                        }//End if
                    }//End if
                }//End k for
            }//End j for
        }//End i for
    }//End transitiveClosure

    /**
    * Performs a search for cycles.
    */
    private void cycleSearch(){
        boolean hasCycle = false;
        for(int i = 0; i < adjMatrix.length && !hasCycle; i++){
            for(int j = 0; j < adjMatrix.length && !hasCycle; j++){
                hasCycle = adjMatrix[i][j] && adjMatrix[j][i];
            }//End for
        }//End for
        String result = hasCycle ? "Cycle detected" : "No cycle detected";
        System.out.println("[Cycle]: " + result);
    }//End cycleSearch

    /**
     * Prints the information as specified in the handout.
     * @param s The id for the start vertex
     * @param d The id for an end vertex that may or may not have a path from s
     */
    private void printGraphStats(int s, int d){
        depthFirstSearch(s, d);
        findSourceDest(s, d);
        transitiveClosure();
        cycleSearch();
    }//End printGraphStats

    /**
     * Inner Comparator class to sort the Vertex lists
     */
    private static class SortById implements Comparator<Vertex> {
        /**
         * Compares vertices based on id values.
         * @param a One of two vertices to compare
         * @param b One of two vertices to compare
         * @return The difference between the two vertices' ids
         */
        public int compare(Vertex a, Vertex b){
            return a.getId() - b.getId();
        }//End compare
    }//End SortById
}//End Graph
