import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Solver class implements a breadth first search (BFS) to solve the problem.
 * It maintains lists of expanded and unexpanded nodes and finds the optimal
 * solution.
 */
public class Solver {

    ArrayList<Node> unexpanded = new ArrayList<Node>(); // Holds unexpanded node list
    ArrayList<Node> expanded = new ArrayList<Node>(); // Holds expanded node list
    Node rootNode; // Node representing initial state

    /**
     * Constructor: Initializes the solver with the initial board state.
     *
     * @param initialBoard An char array representing the initial state of the
     *                     board.
     */
    public Solver(char[] initialBoard) {
        GameState initialState = new GameState(initialBoard);
        rootNode = new Node(initialState);
    }

    /**
     * Solves the problem using breadth first search (BFS).
     * Explores nodes level by level to find the optimal solution.
     *
     * @param output A PrintWriter object to write the solution output.
     */
    public void solve(PrintWriter output) {
        unexpanded.add(rootNode); // add start node
        while (!unexpanded.isEmpty()) {

            Node curr_node = unexpanded.get(0);
            unexpanded.remove(0); // nodes going to be expanded so remove from unexpanded list

            if (curr_node.state.isGoal()) { // check if we found the solution
                reportSolution(curr_node, output); // recurse up the solution to show path
                System.out.println("Solution Found");
                return;
            }

            expanded.add(curr_node); // keep track of nodes that have been expanded as to not re visit them
            ArrayList<GameState> statesToAdd = curr_node.state.possibleMoves(); // get a list of successors
            System.out.println(curr_node.state);

            for (GameState g : statesToAdd) { // iteratre through list of successors

                // skip successors that's been expanded already or are already determine for
                // expandsion
                if (Node.findNodeWithState(expanded, g) == null && Node.findNodeWithState(unexpanded, g) == null) {

                    Node newNode = new Node(g, curr_node, curr_node.getCost() + 1);
                    unexpanded.add(newNode); // add to queue for processing
                }
            }
        }

        output.println("No solution found");
        System.out.println("No solution found");
    }

    /**
     * Recursively prints the solution path from the goal node to the root node.
     *
     * @param n      The goal node.
     * @param output A PrintWriter object to write the output.
     */
    public void printSolution(Node n, PrintWriter output) {
        if (n.parent != null)
            printSolution(n.parent, output);
        output.println(n.state);
    }

    /**
     * Report the solution, including the path, number of moves, and search stats.
     *
     * @param n      The goal node.
     * @param output A PrintWriter object to write the output.
     */
    public void reportSolution(Node n, PrintWriter output) {
        output.println("Solution found!");
        printSolution(n, output);
        output.println(n.getCost() + " Moves");
        output.println("Nodes expanded: " + this.expanded.size());
        output.println("Nodes unexpanded: " + this.unexpanded.size());
        output.println();
    }

    public static void main(String[] args) throws Exception {
        Solver problem = new Solver(GameState.INITIAL_BOARD); // set up the problem to be solved.
        File outFile = new File("output.txt"); // create a file as the destination for output
        PrintWriter output = new PrintWriter(outFile); // create a PrintWriter for that file
        problem.solve(output); // search for and print the solution
        output.close(); // close the PrintWriter
    }
}
