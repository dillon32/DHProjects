package chess;

/**
*This class constructs Pieces representing queens
*@author Dillon Heyck
*/
public class Queen extends Piece
{

	/**
	 * Creates a queen piece
	 * @param color Whether piece is black or white
	 * @param x starting x position
	 * @param y starting y position
	 * @param board board the piece is on
	 */
	public Queen(String color, int x, int y, Board board) {
		super(color, x, y, board);
	}

	public boolean tryMove(int x2, int y2) 
	{
		if(!tryMoveInit(x2,y2))
			return false;
		
		Piece[][] board = this.board.board;
		
		if(this.x == x2){
			int init;
			int end;
			if(this.y > y2)
			{
				init = y2 + 1;
				end = this.y;
			}
			else
			{
				init = this.y + 1;
				end = y2;
			}
			
			for(int i = init; i < end; i++)
			{
				if(board[i][x2] != null)
					return false;
			}
			return true;
		}
		else if(this.y == y2)
		{
			int init;
			int end;
			if(this.x > x2)
			{
				init = x2 + 1;
				end = this.x;
			}
			else
			{
				init = this.x + 1;
				end = x2;
			}
			
			for(int i = init; i < end; i++)
				if(board[y2][i] != null)
					return false;
			return true;
		}
		
		int xDif = Math.abs(x2 - this.x);
		int yDif = Math.abs(y2 - this.y);
				
		if(xDif == yDif)
		{		
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
						
			for(int i = 1; i < xDif; i++)
				if(board[startY + (incY * i)][startX + (incX * i)] != null)
					return false;
			
			return true;
		}
		return false;
	}
	
	public String toString()
	{
		return "Q";
	}

}
