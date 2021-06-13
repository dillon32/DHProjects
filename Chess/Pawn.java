/**
*This class constructs Pieces representing pawns
*@authors Dillon Heyck and Francis Joe
*/
package chess;

public class Pawn extends Piece
{
	/**
	 * Creates a Pawn piece
	 * @param color Whether piece is black or white
	 * @param x starting x position
	 * @param y starting y position
	 * @param board board the piece plays on
	 */
	public Pawn(String color, int x, int y, Board board)
	{
		super(color, x, y, board);
	}

	
	public void move(int x2, int y2)
	{
		if(Math.abs(this.y - y2) == 2)
			hasMoved2 = true;
		else 
			hasMoved2 = false;
		if(canEnpassant(x2,y2))
		{
			board.board[this.y][x2].remove();
			board.board[this.y][x2] = null;
		}
		
		super.move(x2, y2);
	}
	
	public boolean tryMove(int x2, int y2)
	{
		if(!tryMoveInit(x2,y2))
			return false;

		Piece[][] board = this.board.board;
		
		int xDif = Math.abs(x2 - x);
		int yDif = Math.abs(y2 - y);
		
		if(yDif > 2 || yDif <= 0)
			return false;
		if(xDif > 1)
			return false;
		
		if(color.equals("black"))
		{
			if(y2 < this.y)
				return false;
		}
		else
		{
			if(y2 > this.y)
				return false;
		}
		
		if(yDif == 2 && xDif == 1)
			return false;
		else if(yDif == 1 && xDif == 0)
			return board[y2][x2] == null;
		else if(yDif == 2)
		{
			if(color.equals("black"))
			{
				if(board[y+1][x] != null)
					return false;
			}
			else
			{
				if(board[y-1][x] != null)
					return false;
			}
			
			if(board[y2][x2] != null)
				return false;
			return !hasMoved;
		}
		else
		{
			if(board[y2][x2] != null)
				return true;
			else
				return canEnpassant(x2,y2);
				
		}
	}//end tryMove
	
	/**
	 * Checks if current pawn can enpassant if going to given pos
	 * @param x2 x pos to go to
	 * @param y2 y pos to go to
	 * @return if pawn can enpassant by going here
	 */
	public boolean canEnpassant(int x2, int y2)
	{
		Piece[][] board = this.board.board;
		if(board[y2][x2] != null)
			return false;
		Piece otherPawn = board[y][x2];
		return otherPawn != null && otherPawn instanceof Pawn && otherPawn.hasMoved2 && 
				!otherPawn.color.equals(color);
	}
	
	public String toString()
	{
		return "p";
	}


}