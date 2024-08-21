/**
 * author: Mark Sheldon
 */
package cpsc2150.extendedTicTacToe.models;
import java.lang.String;

/**
 * This class is used to store the row and column indices of a marker
 *
 * @invariant None
 */
public class BoardPosition {
    private int row;
    private int column;

    /**
     * Constructs a BoardPosition object populating the row and column fields with
     *          the two ints passed into it
     *
     * @param r The row number
     * @param c The column number
     *
     * @pre None
     * @post row = r AND column = c
     */
    public BoardPosition(int r, int c) {
        row = r;
        column = c;
    }

    /**
     * Returns the row number
     *
     * @return The value of row
     *
     * @pre NONE
     * @post getRow = row AND row = #row AND column = #column
     */
    public int getRow() {return row;}

    /**
     * Returns the column number
     *
     * @return The value of column
     *
     * @pre NONE
     * @post getColumn = column AND row = #row AND column = #column
     */
    public int getColumn() {return column;}

    /**
     * Overridden method that compares two BoardPositions for equality
     *
     * @param positionToCheck The object to be compared to this
     *
     * @return True if positionToCheck.row is equal to this.row and positionToCheck.column is
     *          equal to this.column, false if it is not
     *
     * @pre NONE
     * @post equals = this instanceof BoardPosition && [this.row = positionToCheck.row && this.column = positionToCheck.column] AND
     *          row = #row AND column = #column
     */
    @Override
    public boolean equals(Object positionToCheck) {
        if(!(positionToCheck instanceof BoardPosition)) {
            return false;
        }
        BoardPosition posToCheck = (BoardPosition) positionToCheck;
        if(this.getRow() == posToCheck.getRow() && this.getColumn() == posToCheck.getColumn()) {
            return true;
        }
        return false;
    }

    /**
     * Returns the row and column as a string
     *
     * @return A string containing row then column in format "<row>,<column>"
     *
     * @pre NONE
     * @post toString = [a string representation of row and column like "<row>,<column>"] AND
     *          row = #row AND column = #column
     */
    @Override
    public String toString() {
        return this.getRow() + "," + this.getColumn();
    }
}
