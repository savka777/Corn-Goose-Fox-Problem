//The method solve is empty. Please write code for this method based on the pseudo code for breath first search.

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

/*
   Solver is a class that contains the methods used to search for and print solutions
   plus the data structures needed for the search.
 */

public class Solver {

    ArrayList<Node> queue = new ArrayList<Node>(); // Holds unexpanded node list
    ArrayList<Node> visited = new ArrayList<Node>();   // Holds expanded node list
    Node rootNode;                                      // Node representing initial state

    /*
       Solver is a constructor that sets up an instance of the class with a node corresponding
       to the initial state as the root node.
     */
    public Solver(char[] initialBoard) {
        GameState initialState = new GameState(initialBoard);
        rootNode = new Node(initialState);
    }

    /*
       The method solve searches for a solution. It implements a breadth first search.
       The problem asks for a solution with the minimum number of moves.
       Breadth first search is both complete and optimal with respect to number of moves.
       The Printwriter argument is used to specify where the output should be directed.
     */
    
    public void solve(PrintWriter output) {
        queue.add(rootNode);
        while (!queue.isEmpty()) {

            Node curr_node = queue.get(0);
            queue.remove(0);

            if(curr_node.state.isGoal()) {
                output.println("Solution Found");
                reportSolution(curr_node,output);
                System.out.println("Solution Found");
                return;
            }

            visited.add(curr_node);
            ArrayList<GameState> statesToAdd = curr_node.state.possibleMoves();
            System.out.println(curr_node.state);

            for(GameState g : statesToAdd){
                
                if(Node.findNodeWithState(visited, g) == null && Node.findNodeWithState(queue,g) == null){

                    Node newNode = new Node(g,curr_node,curr_node.getCost() + 1);
                    queue.add(newNode);
                }
            }
        }

        output.println("No solution found");
        System.out.println("No solution found");
    }

    /*
       printSolution is a recursive method that prints all the states in a solution.
       It uses the parent links to trace from the goal to the initial state then prints
       each state as the recursion unwinds.
       Node n should be a node representing the goal state.
       The Printwriter argument is used to specify where the output should be directed.
     */
    public void printSolution(Node n, PrintWriter output) {
        if (n.parent != null) printSolution(n.parent, output);
        output.println(n.state);
    }

    /*
       reportSolution prints the solution together with statistics on the number of moves
       and the number of expanded and unexpanded nodes.
       The Node argument n should be a node representing the goal state.
       The Printwriter argument is used to specify where the output should be directed.
     */
    public void reportSolution(Node n, PrintWriter output) {
        output.println("Solution found!");
        printSolution(n, output);
        output.println(n.getCost() + " Moves");
        output.println("Nodes expanded: " + this.visited.size());
        output.println("Nodes unexpanded: " + this.queue.size());
        output.println();
    }


    public static void main(String[] args) throws Exception {
        Solver problem = new Solver(GameState.INITIAL_BOARD);  // Set up the problem to be solved.
        File outFile = new File("output.txt");                 // Create a file as the destination for output
        PrintWriter output = new PrintWriter(outFile);         // Create a PrintWriter for that file
        problem.solve(output);                                 // Search for and print the solution
        output.close();                                        // Close the PrintWriter (to ensure output is produced).
    }
}
