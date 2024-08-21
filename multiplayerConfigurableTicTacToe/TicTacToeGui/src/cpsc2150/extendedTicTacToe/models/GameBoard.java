/**
 * author: Mark Sheldon
 */
package cpsc2150.extendedTicTacToe.models;

/**
 * @invariant 3 <= board.length <= 100 AND 3 <= board[0].length <= 100 AND 3 <= numToWin <= 25
 * @correspondence Row = board.length AND Column = board[0].length AND NumToWin = numToWin AND
 *      self = board[0...board[0].length-1][0...board[0].length-1]
 */
public class GameBoard extends AbsGameBoard implements IGameBoard {
    private char[][] board;
    private int numToWin;
    private int markersPlayed;

    /**
     * Constructs a GameBoard object with a 2D array that contains ' ' characters
     *
     * @param rows The number of rows on the board
     * @param columns The number of columns on the board
     * @param winNum The number of like markers needed in row to win
     * @pre rows <= MAX_ROW AND columns <= MAX_COL AND winNum <= MAX_WIN
     * @post [board has #rows rows] AND [board has #column columns] AND
     * [board has initialized each character to ' ']
     */
    public GameBoard(int rows, int columns, int winNum) {
        board = new char[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = ' ';
            }
        }
        numToWin = winNum;
        markersPlayed = 0;
    }

    /**
     * Gets the number of rows the board has
     *
     * @return The number of rows the board has
     * @post getNumRows = MAX_ROW AND board = #board
     */
    public int getNumRows() {
        return board.length;
    }

    /**
     * Gets the number of columns the board has
     *
     * @return The number of columns the board has
     * @post getNumColumns = MAX_COL AND board = #board
     */
    public int getNumColumns() {
        return board[0].length;
    }

    /**
     * Gets the number of like markers needed in a row to win
     *
     * @return The number of like markers needed in a row to win
     * @post getNumToWin = [the number of like markers needed to win] AND
     * board = #board
     */
    public int getNumToWin() {
        return numToWin;
    }

    /**
     * Places a player's character in the specified position
     *
     * @param marker The position where the character is to be placed
     * @param player The character that should be put in the array at the
     *               specified position
     * @pre 0 <= marker.getRow() < MAX_ROW AND 0 <= marker.getColumn() < MAX_COL AND
     * [marker's position is available] AND player = [a valid player character]
     * @post board[marker.getRow()][marker.getColumn()] = player AND [board has
     * MAX_ROW rows] AND [board has MAX_COL columns] AND [all other elements of board
     * are unchanged]
     */
    public void placeMarker(BoardPosition marker, char player) {
        board[marker.getRow()][marker.getColumn()] = player;
        markersPlayed++;
    }

    /**
     * Checks what is contained in a position on the board
     *
     * @param pos The position being checked
     * @return The character contained in the specified position of the board
     * @pre 0 <= pos.getRow() < MAX_ROW AND 0 <= pos.getColumn() < MAX_COL
     * @post whatsAtPos = [a valid player character] || ' ' AND board = #board
     */
    public char whatsAtPos(BoardPosition pos) {
        return board[pos.getRow()][pos.getColumn()];
    }

    @Override
    public boolean checkForDraw() {
        if(markersPlayed == getNumRows() * getNumColumns()) {
            return true;
        }
        return false;
    }
}