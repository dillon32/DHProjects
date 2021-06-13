/**
 * class Board is used to represent a
 * chess board composed of a 2D array of pieces
 * from the class Piece
 * @authors Dillon Heyck
 */
package chess;

public class Board
{
	public Piece[][] board;

	public Piece[] whitePieces;
	public Piece[] blackPieces;
	public static final int PIECESKINGPOS = 12; //Position of king in pieces array


	/**
	 * Creates a board filled with pieces at appropriate locations
	 */
	public Board()
	{

		board = new Piece[8][8];

		for(int i = 0; i < 8; i++)
		{
			board[1][i] = new Pawn("black", i, 1, this);
			board[6][i] = new Pawn("white", i, 6, this);
		}
		board[0][0] = new Rook("black", 0, 0, this);
		board[0][7] = new Rook("black", 7, 0, this);
		board[7][0] = new Rook("white", 0, 7, this);
		board[7][7] = new Rook("white", 7, 7, this);

		board[0][1] = new Knight("black", 1, 0, this);
		board[0][6] = new Knight("black", 6, 0, this);
		board[7][1] = new Knight("white", 1, 7, this);
		board[7][6] = new Knight("white", 6, 7, this);

		board[0][2] = new Bishop("black", 2, 0, this);
		board[0][5] = new Bishop("black", 5, 0, this);
		board[7][2] = new Bishop("white", 2, 7, this);
		board[7][5] = new Bishop("white", 5, 7, this);

		board[0][3] = new Queen("black", 3, 0, this);
		board[7][3] = new Queen("white", 3, 7, this);

		board[0][4] = new King("black", 4, 0, this);
		board[7][4] = new King("white", 4, 7, this);

		whitePieces = new Piece[16];
		blackPieces = new Piece[16];

		for(int i = 0; i < 8; i++)
		{
			whitePieces[i] = board[6][i];
			whitePieces[i+8] = board[7][i];
			blackPieces[i] = board[1][i];
			blackPieces[i+8] = board[0][i];
		}
	}

	/**
	 * Returns location of king in y, x format in an int array of size two
	 * @param moveType 1 = white, 0 = black
	 * @return king
	 */
	public Piece getKing(boolean moveType)
	{
		Piece king = null;
		if(moveType)
		{
			for(int i = 0; i < 16; i++)
			{
				if(whitePieces[i] instanceof King)
				{
					king = whitePieces[i];
				}
			}
		} else
		{
			for(int i = 0; i < 16; i++)
			{
				if(blackPieces[i] instanceof King)
				{
					king = blackPieces[i];
				}
			}
		}

		return king;
	}
	
	/**
	 *Traverses board to print text representation
	 */
	public void printBoard()
	{
		int rowNum = 8;

		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				//check if the space on the board does not
				//have a piece
				if(board[i][j] == null)
				{
					if((i + j)%2 == 0)
						System.out.print("   ");
					else
						System.out.print("## ");
				} else //case where space is occupied by a piece
				{
					System.out.print(board[i][j].color.charAt(0));
					System.out.print(board[i][j] + " ");
				}
			}//end inner for loop

			System.out.print(rowNum);
			System.out.println();
			rowNum--;
		}//end outer for loop

		System.out.println(" a  b  c  d  e  f  g  h");
		System.out.println();
	}//end printBoard()

	/**
	 * Morphs pawn that makes it across the board
	 * into the type of piece the user requests.
	 * If no request is made, then the pawn becomes a
	 * Queen by default
	 * @param pawn the pawn to be changed
	 * @param morphTo To specify what player wants
	 * @param board to apply morph
	 */
	void morphPawn(Piece pawn, String morphTo)
	{
		Piece morphed;
		if(morphTo.endsWith("N"))
			morphed = new Knight(pawn.color, pawn.x, pawn.y, this);
		else if(morphTo.endsWith("R"))
			morphed = new Rook(pawn.color, pawn.x, pawn.y, this);
		else if(morphTo.endsWith("B"))
			morphed = new Bishop(pawn.color, pawn.x, pawn.y, this);
		else
			morphed = new Queen(pawn.color, pawn.x, pawn.y, this);
		board[pawn.y][pawn.x] = morphed; 
		Piece[] team;
		if(morphed.color.equals("black"))
			team = blackPieces;
		else
			team = whitePieces;
		
		for(int i = 0; i < 16; i++)
		{
			if(team[i] == pawn)
			{
				team[i] = morphed;
				return;
			}
		}

	}
}
