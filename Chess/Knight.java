package chess;

/**
* Class constructs "knight" pieces
*@author Dillon Heyck
*/
public class Knight extends Piece
{
	/**
	 * Creates a Knight piece
	 * @param color Whether piece is black or white
	 * @param x starting x position
	 * @param y starting y position
	 * @param board board the piece is on
	 */
	public Knight(String color, int x, int y, Board board) {
		super(color, x, y, board);
	}

	public boolean tryMove(int x2, int y2) 
	{
		if(!tryMoveInit(x2,y2))
			return false;
		
		int xDif = Math.abs(x2 - this.x);
		int yDif = Math.abs(y2 - this.y);
		
		if((xDif == 1 && yDif == 2) || (xDif == 2 && yDif == 1))
			return true;
		
		return false;
	}
	
	public String toString()
	{
		return "N";
	}

}
