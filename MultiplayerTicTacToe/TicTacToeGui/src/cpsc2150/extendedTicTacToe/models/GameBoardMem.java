/**
 * author: Mark Sheldon
 */
package cpsc2150.extendedTicTacToe.models;

import java.util.*;

/**
 * @invariant 3 <= rows = #rows <= MAX_ROW AND 3 <= columns = #columns <= MAX_COL AND 3 <= numToWin = #numToWin <= MAX_WIN
 *          AND rows = #rows AND columns = #columns
 * @correspondences Row = rows AND Column = columns AND NumToWin = numToWin AND self = board
 */
public class GameBoardMem extends AbsGameBoard implements IGameBoard {
    private int rows;
    private int columns;
    private int numToWin;
    private Map<Character, List<BoardPosition>> board;

    /**
     * Constructs a GameBoardMem object using a hashmap and initializes the number of
     *      rows, columns, and tiles in a row to win
     *
     * @pre 3 <= numRows <= MAX_ROW AND 3 <= numColumns <= MAX_COL AND 3 <= winNum <= MAX_WIN
     * @post board = HashMap AND [board is empty] AND rows = numRows AND columns = numColumns AND numToWin = winNum
     */
    public GameBoardMem(int numRows, int numColumns, int winNum) {
        rows = numRows;
        columns = numColumns;
        numToWin = winNum;
        board = new HashMap<>();
    }

    /**
     * Gets the number of rows the board has
     *
     * @return The number of rows the board has
     * @post getNumRows = rows AND board = #board
     */
    public int getNumRows()    {return rows;}

    /**
     * Gets the number of columns the board has
     *
     * @return The number of columns the board has
     * @post getNumColumns = columns AND board = #board
     */
    public int getNumColumns() {return columns;}

    /**
     * Gets the number of like markers needed in a row to win
     *
     * @return The number of like markers needed in a row to win
     * @post getNumToWin = [the number of like markers needed to win] AND
     * board = #board
     */
    public int getNumToWin()   {return numToWin;}

    /**
     * Places a player's character in the specified position
     *
     * @param marker The position where the character is to be placed
     * @param player The character that should be put in the array at the
     *               specified position
     * @pre 0 <= marker.getRow() < rows AND 0 <= marker.getColumn() < columns AND
     * [marker's position is available and not already contained in the map] AND
     * [player is a valid player character]
     * @post board.get(player).get(size()-1) = marker AND [a player-list pair is added
     * to the list if it didn't already exist] AND [all other elements of board
     * are unchanged]
     */
    public void placeMarker(BoardPosition marker, char player) {
        if(!board.containsKey(player)) {
            board.put(player, new ArrayList<BoardPosition>());
        }
        board.get(player).add(marker);
    }

    /**
     * Checks what is contained in a position on the board
     *
     * @param pos The position being checked
     * @return The character contained in the specified position of the board
     * @pre 0 <= pos.getRow() < rows AND 0 <= pos.getColumn() < columns
     * @post whatsAtPos = [a valid player character] || ' ' AND board = #board
     */
    public char whatsAtPos(BoardPosition pos) {
        for(char key : board.keySet()) {
            if(board.get(key).contains(pos)) {
                return key;
            }
        }
        return ' ';
    }

    /**
     * Checks if a player has played a marker at the specified position
     *
     * @param pos The position being checked
     * @param player The player's character marker
     * @return True if the player has played at the position, false otherwise
     * @pre 0 <= pos.row < rows AND 0 <= pos.column < columns AND
     *      player = [a valid player character]
     * @post isPlayerAtPos = [true if the player is at the position, false otherwise]
     *      AND board = #board
     */
    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player) {
        if(board.containsKey(player)) {
            if(board.get(player).contains(pos)) {
                return true;
            }
        }
        return false;
    }
}
