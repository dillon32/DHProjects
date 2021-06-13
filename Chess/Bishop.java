package chess;

/**
*This class constructs Pieces representing bishops
*@author Dillon Heyck
*/
public class Bishop extends Piece
{
	/**
	 * Creates a Bishop piece
	 * @param color Whether piece is black or white
	 * @param x starting x position
	 * @param y starting y position
	 * @param board board the piece is on
	 */
	public Bishop(String color, int x, int y, Board board) {
		super(color, x, y, board);
	}

	public boolean tryMove(int x2, int y2) 
	{
		if(!tryMoveInit(x2,y2))
			return false;
		
		int xDif = Math.abs(x2 - this.x);
		int yDif = Math.abs(y2 - this.y);
		
		if(xDif != yDif)
			return false;
		
		int startX = this.x;
		int startY = this.y;
		int incX, incY;

		if(this.x > x2)
			incX = -1;
		else
			incX = 1;
		if(this.y > y2)
			incY = -1;
		else 
			incY = 1;
		
		Piece[][] board = this.board.board;
		
		for(int i = 1; i < xDif; i++)
			if(board[startY + (incY * i)][startX + (incX * i)] != null)
				return false;
		
		return true;
	}
	
	public String toString()
	{
		return "B";
	}

}
