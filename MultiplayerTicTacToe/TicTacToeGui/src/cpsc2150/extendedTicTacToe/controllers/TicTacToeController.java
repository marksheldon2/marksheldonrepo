package cpsc2150.extendedTicTacToe.controllers;

import cpsc2150.extendedTicTacToe.models.*;
import cpsc2150.extendedTicTacToe.views.*;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>
 * The {@link TicTacToeController} class will handle communication between our {@link TicTacToeView}
 * and our model ({@link IGameBoard} and {@link BoardPosition})
 * </p>
 *
 * <p>
 * This is where you will write code
 * <p>
 *
 * You will need to include your {@link BoardPosition} class, the {@link IGameBoard} interface
 * and both of the {@link IGameBoard} implementations from Project 4.
 * If your code was correct you will not need to make any changes to your {@link IGameBoard} implementation class.
 *
 * @version 2.0
 */
public class TicTacToeController {

    /**
     * <p>
     * The current game that is being played
     * </p>
     */
    private IGameBoard curGame;

    /**
     * <p>
     * The screen that provides our view
     * </p>
     */
    private TicTacToeView screen;

    /**
     * <p>
     * Constant for the maximum number of players.
     * </p>
     */
    public static final int MAX_PLAYERS = 10;

    /**
     * <p>
     * List of all possible player markers (up to 10 players)
     * </p>
     */
    public static final char[] POSSIBLE_MARKERS = {'X', 'O', 'A', 'M', 'S', 'T', 'Y', 'Z', 'B', 'J'};

    /**
     * <p>
     * The number of players for this game. Note that our player tokens are hard coded.
     * </p>
     */
    private int numPlayers;

    /**
     * <p>
     * A collection of the tokens used in the current game to represent each player
     * </p>
     */
    private Queue<Character> playerMarkers;

    /**
     * <p>
     * The player whose turn it is
     * </p>
     */
    private Character currPlayer;

    /**
     * <p>
     * Indicates whether a new game is being started upon a button click. false = no new game, true = new game
     * </p>
     */
    private boolean newGameClick;

    /**
     * <p>
     * This creates a controller for running the Extended TicTacToe game
     * </p>
     *
     * @param model
     *      The board implementation
     * @param view
     *      The screen that is shown
     * @param np
     *      The number of players for this game.
     *
     * @post [ the controller will respond to actions on the view using the model. ]
     */
    public TicTacToeController(IGameBoard model, TicTacToeView view, int np) {
        this.curGame = model;
        this.screen = view;
        this.numPlayers = np;
        playerMarkers = new LinkedList<Character>();
        newGameClick = false;
        currPlayer = 'X';

        // adding the necessary number of player markers into the queue to cycle turns throughout the game
        // index starts at 1 because the next player will be the second player, 'O'
        for(int i = 1; i < numPlayers; i++) {
            playerMarkers.add(POSSIBLE_MARKERS[i]);
        }
        // the player marker of player 'X' (who takes the first turn) must be at the end of the queue
        playerMarkers.add(POSSIBLE_MARKERS[0]);
    }

    /**
     * <p>
     * This processes a button click from the view.
     * </p>
     *
     * @param row
     *      The row of the activated button
     * @param col
     *      The column of the activated button
     *
     * @post [ will allow the player to place a marker in the position if it is a valid space, otherwise it will display an error
     * and allow them to pick again. Will check for a win as well. If a player wins it will allow for them to play another
     * game hitting any button ]
     */
    public void processButtonClick(int row, int col) {
        // Check if a new game needs to be started
        if(newGameClick) {
            newGameClick = false;
            newGame();
        }

        BoardPosition boardPos = new BoardPosition(row, col);

        // is the space available?
        if(!curGame.checkSpace(boardPos)) {
            screen.setMessage("Space unavailable, please choose again.");
        }
        else {
            curGame.placeMarker(boardPos, currPlayer);
            screen.setMarker(row, col, currPlayer);
            if(curGame.checkForWinner(boardPos)){
                screen.setMessage("Player " + currPlayer + " wins!");
                newGameClick = true;
            }
            else if(curGame.checkForDraw()) {
                screen.setMessage("It's a draw!");
                newGameClick = true;
            }
            else {
                // get next player's character
                currPlayer = playerMarkers.remove();
                screen.setMessage("It is " + currPlayer + "'s turn. ");
                // place player at the end of the queue
                playerMarkers.add(currPlayer);
            }
        }
    }

    /**
     * <p>
     * This method will start a new game by returning to the setup screen and controller
     * </p>
     *
     * @post [ a new game gets started ]
     */
    private void newGame() {
        //close the current screen
        screen.dispose();

        //start back at the set up menu
        GameSetupScreen screen = new GameSetupScreen();
        GameSetupController controller = new GameSetupController(screen);
        screen.registerObserver(controller);
    }
}