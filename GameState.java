import java.util.ArrayList;

/**
 * GameState class represents a state in the problem space.
 * It includes the board configuration and methods to generate valid moves.
 */
public class GameState {
    final char[] board;
    private char farmerPlace; // tracks the farmer's position (L or R)
    static final char[] INITIAL_BOARD = { 'L', 'L', 'L', 'L' }; // initial board configuration
    static final char[] GOAL_BOARD = { 'R', 'R', 'R', 'R' }; // goal board
    static final int BOARD_SIZE = 4;

    // For better readbility and clarity of positions
    static final int CORN = 0;
    static final int GOOSE = 1;
    static final int FOX = 2;
    static final int FARMER = 3;

    /**
     * Constructor: Initializes the game state with a given board configuration.
     *
     * @param board The board configuration as a char array.
     */
    public GameState(char[] board) {
        this.board = board;
        this.farmerPlace = board[FARMER];
    }

    /**
     * Creates a copy of the current game state.
     *
     * @return A new GameState object with the same board configuration.
     */
    public GameState clone() {
        char[] clonedBoard = new char[BOARD_SIZE];
        System.arraycopy(this.board, 0, clonedBoard, 0, BOARD_SIZE);
        return new GameState(clonedBoard);
    }

    public char getFarmerPlace() {
        return farmerPlace;
    }

    public String toString() {
        return "Corn: " + board[CORN] +
                ", Goose: " + board[GOOSE] +
                ", Fox: " + board[FOX] +
                ", Farmer: " + board[FARMER];
    }

    /**
     * Checks if the current state is the goal state.
     *
     * @return True if the board matches the goal configuration, otherwise false.
     */
    public boolean isGoal() {
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (this.board[j] != GOAL_BOARD[j])
                return false;
        }
        return true;
    }

    /**
     * Compares the current state with another state.
     *
     * @param gs The GameState to compare with.
     * @return True if the states are identical, otherwise false.
     */
    public boolean sameBoard(GameState gs) {
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (this.board[j] != gs.board[j])
                return false;
        }
        return true;
    }

    /**
     * Validates if the current move adheres to the pre and post conditions.
     *
     * @return True if the move is valid, otherwise false.
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

    /**
     * Generates all valid moves from the current state.
     *
     * @return A list of valid GameState objects representing possible moves.
     */
    public ArrayList<GameState> possibleMoves() {
        ArrayList<GameState> moves = new ArrayList<GameState>();
        for (int start = 0; start < BOARD_SIZE - 1; start++) { // iterate through the board

            // board[0] = Corn which can be on the L or R side, if the farmer is on the same
            // side, that means we can take that item across and so on for the rest
            if (this.board[start] == this.farmerPlace) { // valid move
                GameState newState = this.clone(); // create a new (state), this is a child of the parent
                if (this.farmerPlace == 'L') { // if the farmer is on the left
                    newState.board[start] = 'R'; // move the entity (corn for example) to the right
                    newState.board[FARMER] = 'R'; // move the farmer to the right as well
                    newState.farmerPlace = 'R'; // update the farmers position
                } else {
                    newState.board[start] = 'L'; // if the entity is on the left
                    newState.board[FARMER] = 'L'; // move the farmers position to left
                    newState.farmerPlace = 'L'; // update the farmers position
                }
                if (!newState.sameBoard(this)) { // add new states if valid and not duplicate
                    if (newState.isValidMove()) { // check pre and post conditions
                        moves.add(newState);
                    }
                }

            }
        }
        // farmer crosses alone
        GameState newState = this.clone();
        if (this.farmerPlace == 'L') { // check if farmer is on the left side
            newState.board[FARMER] = 'R'; // move the farmer to the right side
            newState.farmerPlace = 'R'; // update the farmers position
        } else { // do the same but for the right side
            newState.board[FARMER] = 'L';
            newState.farmerPlace = 'L';
        }
        if (newState.isValidMove())
            moves.add(newState);

        return moves;
    }
}
