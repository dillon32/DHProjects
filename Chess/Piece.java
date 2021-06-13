package chess;

/**
 * Abstract class that all pieces (ie Pawn, rook) all extend as they all share 
 * same constructors and methods
 *@author Dillon Heyck
 *
 */
public abstract class Piece {

	String color;
	int x, y;
	Board board;
	boolean hasMoved = false;
	boolean hasMoved2 = false;

	/**
	 * Sees if the piece can move to a position on a board
	 * @param x2 x position to check
	 * @param y2 y position to check
	 * @return whether piece can move to desired position
	 */
	public abstract boolean tryMove(int x2, int y2);
	
	/**
	 * Moves piece to position
	 * @param x x position to move to
	 * @param y y position to move to
	 */
	public void move(int x, int y)
	{
		Piece[][] board = this.board.board;
		board[this.y][this.x] = null;
		hasMoved = true;
		this.x = x;
		this.y = y;
		
		if(board[y][x] != null)
			board[y][x].remove();
		
		board[y][x] = this;
	}
	
	/**
	 * Checks whether position is out of bounds, if it the same as the current position, 
	 * or if a piece on the same team resides at the position. 
	 * Essentially, tryMoveInit checks for illegal movement that is common for all pieces
	 * @return preliminary determination of if piece can move to location
	 */
	protected boolean tryMoveInit(int x2, int y2)
	{
		if(x2 > 7 || x2 < 0)
			return false;
		if(y2 > 7 || y2 < 0)
			return false;
		
		if(this.x == x2 && this.y == y2)
			return false;
		
		Piece[][] board = this.board.board;
		
		if(board[y2][x2] != null && board[y2][x2].color.equals(this.color))
			return false;
		return true;
	}
	
	/**
	 * Removes this piece from the team's pieces
	 */
	protected void remove()
	{
		Piece[] removingFrom;
		if(this.color.equals("black"))
			removingFrom = board.blackPieces;
		else
			removingFrom = board.whitePieces;
		
		
		for(int i = 0; i < 16; i++)
			if(removingFrom[i] == this)
			{
				removingFrom[i] = null;
				return;
			}
	}
	
	/**
	 * Constructor of Piece class
	 * @param color Whether piece is black or white
	 * @param x starting x position 
	 * @param y starting y position
	 * @param board board that piece is put on
	 */
	public Piece(String color, int x, int y, Board board){
		this.color = color;
		this.x = x;
		this.y = y;
		this.board = board;
	}
}
