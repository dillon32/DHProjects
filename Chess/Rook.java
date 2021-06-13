package chess;

/**
* Class constructs "rook" pieces
*@author Dillon Heyck
*/
public class Rook extends Piece
{
	/**
	 * Creates a rook piece
	 * @param color Whether piece is black or white
	 * @param x starting x position
	 * @param y starting y position
	 * @param board board the piece is on
	 */
	public Rook(String color, int x, int y, Board board){
		super(color, x, y, board);
	}

	
	public boolean tryMove(int x2, int y2) {
		if(!tryMoveInit(x2,y2))
			return false;
		
		Piece[][] board = this.board.board;
		
		//Checks if another piece is in between start and end positions
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
		}
		//End pos is not a legal move rooks can make
		else 
		{
			return false;

		}
		
		return true;
	}
	
	public String toString()
	{
		return "R";
	}
}
