import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.exit;

public class Driver {

    /**
     * Creates a Graph object to use the iterative algorithms discussed in class
     * to perform a depth-first search (DFS), transitive closure (TC),
     * and cycle search on a directed graph.
     * @param args Input file containing data about a directed graph
     */
    public static void main(String[] args){
        try {
            ArrayList<Integer> input = getInput(args[0]);
            int[] primInput = new int[input.size()];
            for(int i = 0; i < input.size(); i++){
                primInput[i] = input.get(i);
            }//End for

            Graph graph = new Graph(primInput);
            graph.go();
        }catch(InputMismatchException | IllegalArgumentException e){
            System.out.println("The input file has been found to be formatted incorrectly. " +
                    "Please see the README.txt file for acceptable input file formats.");
            exit(1);
        }//End catch
    }//End main

    /**
     * Attempts to read the file given and return an ArrayList of integers
     * @param file The input file name as a string
     * @return A string containing the full text of the input stream
     */
    private static ArrayList<Integer> getInput(String file){
        ArrayList<Integer> input = new ArrayList<>();
        Scanner reader = null;
        try {
            reader = new Scanner(new FileReader(file));
            while(reader.hasNext()){
                input.add(reader.nextInt());
            }//End while
        } catch (FileNotFoundException e) {
            System.out.println("Error: The file could not be found");
            exit(1);
        }finally{
            if(reader != null){
                reader.close();
            }//End if
        }//End try/catch/finally
        return input;
    }//End getText
}//End Driver
