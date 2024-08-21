/**
 * author: Mark Sheldon
 */
package cpsc2150.extendedTicTacToe.models;

/**
 * Stores a representation of a tic-tac-toe board and provides functionality to check for wins,
 *  draws, and space occupation
 *
 * @defines: Row: Z
 *          Column: Z
 *          NumToWin: Z
 * @initialization_ensures: Board will have Row # of rows AND Board will have Column # of columns
 *      AND Board will be empty
 * @constraints: 3 <= Row <= MAX_ROW AND 3 <= Column <= MAX_COLUMN
 */
public interface IGameBoard {
    public static final int MAX_ROW = 100;
    public static final int MAX_COL = 100;
    public static final int MAX_WIN = 25;

    /**
     * Gets the number of rows the board has
     *c
     * @return The number of rows the board has
     * @post getNumRows = [number of rows on the board] AND self = #self
     */
    int getNumRows();

    /**
     * Gets the number of columns the board has
     *
     * @return The number of columns the board has
     * @post getNumColumns = [number of columns on the board] AND self = #self
     */
    int getNumColumns();

    /**
     * Gets the number of like markers needed in a row to win
     *
     * @return The number of like markers needed in a row to win
     * @post getNumToWin = [the number of like markers needed to win] AND
     *      self = #self
     */
    int getNumToWin();

    /**
     * Checks if a space on the board is available to play on
     *
     * @param pos The position on the board to be checked for availability
     * @return True if the space is available, false otherwise
     * @pre 0 <= pos.row < getNumRows() AND 0 <= pos.column < getNumColumns()
     * @post checkSpace = [true if the position is available, false otherwise] AND
     *      self = #self
     */
     default boolean checkSpace(BoardPosition pos) {
         return pos.getRow() >= 0 && pos.getRow() < getNumRows() && pos.getColumn() >= 0 &&
                 pos.getColumn() < getNumColumns() && whatsAtPos(pos) == ' ';
     }

    /**
     * Places a player's character in the specified position
     *
     * @param marker The position where the character is to be placed
     * @param player The marker that should be placed on the board at the
     *      specified position
     * @pre 0 <= marker.row < getNumRows() AND 0 <= marker.column < getNumColumns() AND
     *      [position is available] AND player = [a valid player character]
     * @post [self = #self except player will be placed at the position specified by marker]
     */
     void placeMarker(BoardPosition marker, char player);

    /**
     * Checks if there is a winner after a marker is added to the board
     *
     * @param lastPos The position of the marker that was just added to the board
     * @return True if there is a winner, false if not
     * @pre [lastPos is the position of a marker on the board] AND [lastPos is the
     *      most recently placed marker] AND 0 <= lastPos.row < getNumRows() AND
     *      AND 0 <= lastPos.column < getNumColumns()
     * @post checkForWinner = [true if the last placed marker met the maximum number
     *      of consecutive same markers vertically, horizontally, or diagonally,
     *      false otherwise] AND self = #self
     */
    default boolean checkForWinner(BoardPosition lastPos) {
         char player = whatsAtPos(lastPos);
         if(checkHorizontalWin(lastPos, player)) {
             return true;}
         if(checkVerticalWin(lastPos, player))   {
             return true;}
         if(checkDiagonalWin(lastPos, player))   {
             return true;}
         return false;
     }

    /**
     * Checks if there is a draw (no spaces remaining on the board without a win)
     *
     * @return True if there is a draw, false if not
     * @pre [The game doesn't have a winner yet]
     * @post checkForDraw = [true if there are no available spaces left on the board,
     *      false otherwise] AND self = #self
     */
     default boolean checkForDraw() {
         int numRows = getNumRows();
         int numCols = getNumColumns();
         BoardPosition currPos;
         for(int i = 0; i < numRows; i++) {
             for(int j = 0; j < numCols; j++) {
                 currPos = new BoardPosition(i, j);
                 if(whatsAtPos(currPos) == ' ') {
                     return false;
                 }
             }
         }
         return true;
     }

    /**
     * Checks for a win horizontally from the last played marker
     *
     * @param lastPos The position of the last played marker
     * @param player The character of the player who last played
     * @return True if there is a horizontal win, false if not
     * @pre [lastPos is the position of a marker on the board] AND [lastPos is the
     *      most recently placed marker] AND 0 <= lastPos.row < getNumRows() AND
     *      AND 0 <= lastPos.column < getNumColumns() AND player = [a valid player character]
     * @post checkHorizontalWin = [true if there is a horizontal win, false if not]
     *      AND board = #board
     */
     default boolean checkHorizontalWin(BoardPosition lastPos, char player) {
         int x = lastPos.getRow();
         int y = lastPos.getColumn();
         int leftCount = 0;
         int rightCount = 0;
         int j = 1;
         BoardPosition currPos = new BoardPosition(x, y-j);
         // Checks for like markers to the left of the last played marker
         while(y-j >= 0 && isPlayerAtPos(currPos, player)){
             leftCount++;
             // plus 1 to include the last placed marker we're checking from
             if(leftCount + rightCount + 1 == getNumToWin()) {return true;}
             j++;
             currPos = new BoardPosition(x, y-j);
         }
         j = 1;
         currPos = new BoardPosition(x, y+j);
         // Checks for like markers to the right of the last played marker
         while(y+j < getNumColumns() && isPlayerAtPos(currPos, player)){
             rightCount++;
             // plus 1 to include the last placed marker we're checking from
             if(leftCount + rightCount + 1 == getNumToWin()) {return true;}
             j++;
             currPos = new BoardPosition(x, y+j);
         }
         return false;
     }

    /**
     * Checks for a win vertically from the last played marker
     *
     * @param lastPos The position of the last played marker
     * @param player The character of the player whose move is being checked for winning
     * @return True if there is a vertical win, false if not
     * @pre [lastPos is the position of a marker on the board] AND [lastPos is the
     *      most recently placed marker] AND 0 <= lastPos.row < getNumRows() AND
     *      AND 0 <= lastPos.column < getNumColumns() AND player = [a valid player character]
     * @post checkVerticalWin = [true if there is a vertical win, false if not]
     *      AND self = #self
     */
     default boolean checkVerticalWin(BoardPosition lastPos, char player) {
         int x = lastPos.getRow();
         int y = lastPos.getColumn();
         int upCount = 0;
         int downCount = 0;
         int i = 1;
         BoardPosition currPos = new BoardPosition(x-i, y);
         // Checks for like markers above the last played marker
         while(x-i >= 0 && isPlayerAtPos(currPos, player)){
             upCount++;
             // plus 1 to include the last placed marker we're checking from
             if(upCount + downCount + 1 == getNumToWin()) {return true;}
             i++;
             currPos = new BoardPosition(x-i, y);
         }
         i = 1;
         currPos = new BoardPosition(x+i, y);
         // Checks for like markers below the last played marker
         while(x+i < getNumRows() && isPlayerAtPos(currPos, player)){
             downCount++;
             // plus 1 to include the last placed marker we're checking from
             if(upCount + downCount + 1 == getNumToWin()) {return true;}
             i++;
             currPos = new BoardPosition(x+i, y);
         }
         return false;
     }

    /**
     * Checks for a win that occurred on one of the two diagonals from the last
     *      played marker
     *
     * @param lastPos The position of the last played marker
     * @param player The character of the player whose move is being checked for winning
     * @return True if there is a diagonal win, false if not
     * @pre [lastPos is the position of a marker on the board] AND [lastPos is the
     *      most recently placed marker] AND 0 <= lastPos.row < getNumRows() AND
     *      AND 0 <= lastPos.column < getNumColumns() AND player = [a valid player character]
     * @post checkDiagonalWin = [true if there is a diagonal win, false if not]
     *      AND self = #self
     */
     default boolean checkDiagonalWin(BoardPosition lastPos, char player) {
         int x = lastPos.getRow();
         int y = lastPos.getColumn();
         // count variables keep track of the successive matching markers extending in their respective direction
         int leftUpCount = 0;
         int leftDownCount = 0;
         int rightUpCount = 0;
         int rightDownCount = 0;
         // i and j are used as the displacement from lastPos
         int i = 1;
         int j = 1;
         BoardPosition currPos = new BoardPosition(x-i, y-j);

         // checks the number of "player" markers extending to the upper left of the last played marker
         while(x-i >= 0 && y-j>=0 && whatsAtPos(currPos) == player){
             leftUpCount++;
             // plus 1 to include the last placed marker we're checking from
             if(leftUpCount + rightDownCount + 1 == getNumToWin()) {
                 return true;
             }
             i++;
             j++;
             currPos = new BoardPosition(x-i, y-j);
         }

         i = j = 1;
         currPos = new BoardPosition(x+i, y+j);
         // checks the number of "player" markers extending to the lower right of the last played marker
         while(x+i<getNumRows() && y+j<getNumColumns() && whatsAtPos(currPos) == player) {
             rightDownCount++;
             // plus 1 to include the last placed marker we're checking from
             if(leftUpCount + rightDownCount + 1 == getNumToWin()) {
                 return true;
             }
             i++;
             j++;
             currPos = new BoardPosition(x+i, y+j);
         }

         i = j = 1;
         currPos = new BoardPosition(x+i, y-j);
         // checks the number of "player" markers extending to the lower left of the last played marker
         while(x+i<getNumRows() && y-j>=0 && whatsAtPos(currPos) == player) {
             leftDownCount++;
             // plus 1 to include the last placed marker we're checking from
             if(leftDownCount + rightUpCount + 1 == getNumToWin()) {
                 return true;
             }
             i++;
             j++;
             currPos = new BoardPosition(x+i, y-j);
         }

         i = j = 1;
         currPos = new BoardPosition(x-i, y+j);
         // checks the number of "player" markers extending to the upper right of the last played marker
         while(x-i>=0 && y+j<getNumColumns() && whatsAtPos(currPos) == player) {
             rightUpCount++;
             if(leftDownCount + rightUpCount + 1 == getNumToWin()) {
                 return true;
             }
             i++;
             j++;
             currPos = new BoardPosition(x-i, y+j);
         }
         return false;
     }

    /**
     * Checks what is contained at a position on the board
     *
     * @param pos The position being checked
     * @return The character contained in the specified position of the board
     * @pre 0 <= pos.row < getNumRows() AND 0 <= pos.column < getNumColumns()
     * @post whatsAtPos = [a valid player character] || ' ' AND self = #self
     */
     char whatsAtPos(BoardPosition pos);

    /**
     * Checks if a player has played a marker at the specified position
     *
     * @param pos The position being checked
     * @param player The player's character marker, 'X' or 'O'
     * @return True if the player has played at the position, false otherwise
     * @pre 0 <= pos.row < getNumRows() AND 0 <= pos.column < getNumColumns() AND
     *      player = [a valid player character]
     * @post isPlayerAtPos = [true if the player is at the position, false otherwise]
     *      AND self = #self
     */
     default boolean isPlayerAtPos(BoardPosition pos, char player) {
         return whatsAtPos(pos) == player;
     }
}
