import java.util.ArrayList;

/**
 * Node class represents a state in the search space.
 * It contains a GameState, a reference to its parent node, and the cost to
 * reach this node.
 */
public class Node {
    GameState state; // the game state associated with the node
    Node parent; // parent node to trace path
    private int cost; // the cost of reaching this node from the initial node, i.e., number of moves.

    /**
     * Constructor for creating a new node with a state, parent, and cost.
     *
     * @param state  The game state associated with this node.
     * @param parent The parent node.
     * @param cost   The cost to reach this node.
     */
    public Node(GameState state, Node parent, int cost) {
        this.state = state;
        this.parent = parent;
        this.cost = cost;
    }

    /**
     * Constructor for creating the initial node (root node).
     *
     * @param state The initial game state.
     */
    public Node(GameState state) {
        this(state, null, 0);
    }

    public int getCost() {
        return cost;
    }

    public String toString() {
        return "Node:" + state + " ";
    }

    /**
     * Searches a list of nodes for a node with a specific game state.
     *
     * @param nodeList The list of nodes to search.
     * @param gs       The game state to search for.
     * @return The node with the matching state, or null if not found.
     */
    public static Node findNodeWithState(ArrayList<Node> nodeList, GameState gs) {
        for (Node n : nodeList) {
            if (gs.sameBoard(n.state))
                return n;
        }
        return null;
    }
}
