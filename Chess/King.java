package chess;

/**
* Class constructs "king" piece
* @author Dillon Heyck
*/
public class King extends Piece
{
	/**
	 * Creates a King piece
	 * @param color Whether piece is black or white
	 * @param x starting x position
	 * @param y starting y position
	 * @param board board the piece is on
	 */
	public King(String color, int x, int y, Board board) {
		super(color, x, y, board);
	}

	public boolean tryMove(int x2, int y2) 
	{
		if(!tryMoveInit(x2,y2))
			return false;
		
		int xDif = Math.abs(x2 - this.x);
		int yDif = Math.abs(y2 - this.y);
		
		if((xDif < 2) && (yDif < 2))
			return true;
		else
			return canCastle(x2, y2);
	}
	
	public void move(int x2, int y2)
	{
		if(canCastle(x2, y2))
		{
			Piece[][] board = this.board.board;
			Piece rookToMove;
			int newRookX;

			if(x > x2)
			{
				rookToMove = board[y2][0];
				newRookX = 3;
			}
			else
			{
				rookToMove = board[y2][7];
				newRookX = 5;
			}
			
			rookToMove.move(newRookX, y2);
			
		}
		super.move(x2, y2);
	}
	
	/**
	 * Checks if piece can commence castling at this location
	 * @param x2 x position for castling
	 * @param y2 y position for castling
	 * @return if this king cas castle to this location
	 */
	public boolean canCastle(int x2, int y2)
	{
		if(hasMoved)
			return false;
		if(y2 != y)
			return false;
		if(Math.abs(x2 - x) > 2)
			return false;
		Piece[][] board = this.board.board;
		
		Piece[] thisTeam;
		Piece[] otherTeam;
		if(this.color.equals("black"))
		{
			thisTeam = super.board.blackPieces;
			otherTeam = super.board.whitePieces;
		}
		else
		{
			thisTeam = super.board.whitePieces;
			otherTeam = super.board.blackPieces;
		}
		
		if(Chess.isCheck(thisTeam, otherTeam))
			return false;
		
		Piece rookToMove;

		if(x > x2)
			rookToMove = board[y2][0];
		else
			rookToMove = board[y2][7];
		if(rookToMove == null)
			return false;
		if(!(rookToMove instanceof Rook) )
			return false;
		if(rookToMove.hasMoved)
			return false;
		
		int start, end;
		if(x > x2)
		{
			start = 0;
			end = x;
		}
		else
		{
			start = x;
			end = 7;
		}
		for(int i = start + 1; i < end; i++)
		{
			if(board[y2][i] != null)
				return false;
		}
		
		return true;
	}
	
	public String toString()
	{
		return "K";
	}

}
