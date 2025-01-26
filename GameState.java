// This class implements the state space representation of the corn, goose and fox problem. 
// Please write comments where appropriate on the state space, initial state, goal state, operations and transition functions. 

import java.util.ArrayList;

/*
      Instances of the class GameState represent states that can arise in the sliding block puzzle.
      The char array board represents the locations (L or R) of Corn, Goose, Fox, and Farmer respectively. 
      INITIAL_BOARD and GOAL_BOARD are constant arrays holding the initial and goal states (board configurations).
 */

public class GameState {
    final char[] board;
    private char farmerPlace; // not really necessary. the same as board[BOARD_SIZE-1]
    static final char[] INITIAL_BOARD = { 'L', 'L', 'L', 'L' };
    static final char[] GOAL_BOARD = { 'R', 'R', 'R', 'R' };
    static final int BOARD_SIZE = 4;

    // For better readbility and clarity of positions
    static final int CORN = 0;
    static final int GOOSE = 1;
    static final int FOX = 2;
    static final int FARMER = 3;

    /*
     * GameState is a constructor that takes a char array holding a board
     * configuration as argument.
     */
    public GameState(char[] board) {
        this.board = board;
        this.farmerPlace = board[FARMER];
    }

    /*
     * clone returns a new GameState with the same board configuration as the
     * current GameState.
     */
    public GameState clone() {
        char[] clonedBoard = new char[BOARD_SIZE];
        System.arraycopy(this.board, 0, clonedBoard, 0, BOARD_SIZE);
        return new GameState(clonedBoard);
    }

    public char getFarmerPlace() {
        return farmerPlace;
    }

    /*
     * toString returns the board configuration of the current GameState as a
     * printable string.
     */
    // public String toString() {
    // String s = "[";
    // for (char c : this.board)
    // s = s + c;
    // return s + "]";
    // }

    public String toString() {
        return "Corn: " + board[CORN] +
                ", Goose: " + board[GOOSE] +
                ", Fox: " + board[FOX] +
                ", Farmer: " + board[FARMER];
    }

    /*
     * isGoal returns true if and only if the board configuration of the current
     * GameState is the goal
     * configuration.
     */
    public boolean isGoal() {
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (this.board[j] != GOAL_BOARD[j])
                return false;
        }
        return true;
    }

    /*
     * sameBoard returns true if and only if the GameState supplied as argument has
     * the same board
     * configuration as the current GameState.
     */
    public boolean sameBoard(GameState gs) {
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (this.board[j] != gs.board[j])
                return false;
        }
        return true;
    }

    /*
     * Helper method to make sure that if we add a possible move, it does not
     * voilate the rules
     * Rule 1: Goose and Fox can NOT be left alone
     * Rule 2: Goose and Corn can NOT be left alone
     */

    public boolean isValidMove() {
        if (board[FARMER] == 'L') {
            if (board[GOOSE] == 'R' && board[CORN] == 'R')
                return false;
            if (board[GOOSE] == 'R' && board[FOX] == 'R')
                return false;
        } else {
            if (board[GOOSE] == 'L' && board[CORN] == 'L')
                return false;
            if (board[GOOSE] == 'L' && board[FOX] == 'L')
                return false;
        }
        return true;
    }

    /*
     * Returns all POSSIBLE moves that can be made from the CURRENT node (game
     * state)
     * This is based on the farmers position's and the rules of the problem
     * 
     * This implements 4 possible move:
     * 
     * Farmer takes 1 item across Corn, Goose, Fox (3 moves)
     * Farmer Crosses alone (1 move)
     * 
     */
    public ArrayList<GameState> possibleMoves() { // unexpanded nodes (children of the current node)
        ArrayList<GameState> moves = new ArrayList<GameState>();
        for (int start = 0; start < BOARD_SIZE - 1; start++) { // loop through the first 3 entries (Corn, Goose, Fox)

            // board[0] = Corn which can be on the L or R side, if the farmer is on the same
            // side, that means we can take that item across
            if (this.board[start] == this.farmerPlace) {
                GameState newState = this.clone(); // Create a new node (state), this is a child of the parent (current
                                                   // node/state)
                if (this.farmerPlace == 'L') { // if the farmer is on the left
                    newState.board[start] = 'R'; // move the entity (corn for example) to the right
                    newState.board[FARMER] = 'R'; // move the farmer to the right as well
                    newState.farmerPlace = 'R'; // update the farmers position
                } else {
                    newState.board[start] = 'L'; // if the entity is on the left
                    newState.board[FARMER] = 'L'; // move the farmers position to left
                    newState.farmerPlace = 'L'; // update the farmers position
                }
                if (!newState.sameBoard(this)) { // dont add states that have previous states already
                    if (newState.isValidMove()) {
                        moves.add(newState); // add child to the list of children this parent (current state/node can have)
                    }
                }

            }
        }
        // If the farmer is on the other side of this river with this entity,
        GameState newState = this.clone();
        if (this.farmerPlace == 'L') { // check if farmer is on the left side
            newState.board[FARMER] = 'R'; // move the farmer to the right side
            newState.farmerPlace = 'R'; // update the farmers position
        } else { // do the same but for the right side
            newState.board[FARMER] = 'L';
            newState.farmerPlace = 'L';
        }
        if(newState.isValidMove())
            moves.add(newState);
            
        return moves;
    }
}
