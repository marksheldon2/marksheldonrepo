/**
 * author: Mark Sheldon
 */
package cpsc2150.extendedTicTacToe.models;

public abstract class AbsGameBoard implements IGameBoard{

    /**
     * Overrides the Object method toString to return a string of the 2D array board
     *      formatted as a table
     *
     * @return A string containing the 2D array formatted as a table
     * @pre None
     * @post toString = [a string of the 2D array as a table] AND board = #board
     */
    @Override
    public String toString() {
        String str = "   ";
        BoardPosition currPos;
        int numRows = getNumRows();
        int numColumns = getNumColumns();
        for (int i = 0; i < numColumns; i++) {
            if(i < 10) {str = str + " " + i + "|";}
            else       {str = str + i + "|";}
        }
        for (int i = 0; i < numRows; i++) {
            if(i < 10) {str = str + "\n " + i + "|";}
            else       {str = str + "\n" + i + "|";}
            for (int j = 0; j < numColumns; j++) {
                currPos = new BoardPosition(i, j);
                str = str + whatsAtPos(currPos) + " |";
            }
        }
        str += "\n";
        return str;
    }
}
