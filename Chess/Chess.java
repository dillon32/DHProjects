/**
 * This is a game of chess that receives
 * input from a text file and visualizes the
 * game with basic ASCII.
 * CS 213 Rutgers University
 * TA Tugba Kulahcioglu
 * @authors Dillon Heyck
 */
package chess;


import java.util.Scanner;

public class Chess {

	private static Board game;

	/**
	 * Initiates playGame()
	 * @param args args
	 */
	public static void main(String[] args){

		playGame();

	}

	/**
	 * Creates a runs a game of chess
	 */
	public static void playGame()
	{
		game = new Board();
		Piece[][] board = game.board;

		Scanner in = new Scanner(System.in);

		//0 = black player's move
		//1 = white player's move
		boolean moveType = true;

		//-1 = game continues
		//0 = black wins
		//1 = white wins
		//2 = draw
		int winner = -1;

		boolean draw = false;

		game.printBoard();

		while(winner == -1)
		{
			Piece[] team;
			if(moveType)
			{
				System.out.print("White's move: ");
				team = game.whitePieces;
			}
			else
			{
				System.out.print("Black's move: ");
				team = game.blackPieces;
			}

			for(int i = 0; i < 16; i++)
				if(team[i] != null)
					team[i].hasMoved2 = false;

			String input = in.nextLine();
			System.out.println();

			if(input.equals("resign"))
			{
				if(moveType)
					winner = 0;
				else
					winner = 1;					
			}

			else if(input.equals("draw"))
			{
				if(draw)
					winner = 2;
				else
				{
					System.out.println("Illegal move, try again");
					System.out.println();
				}
			}

			else
			{
				int[] moveset = translateFileRank(input);
				int startX = moveset[0];
				int startY = moveset[1];
				int endX = moveset[2];
				int endY = moveset[3];

				Piece toMove = board[startY][startX];
				if(toMove != null)
				{
					if((moveType && toMove.color.equals("black")) || 
							(!moveType && toMove.color.equals("white")))
					{
						System.out.println("Illegal move, try again");
						System.out.println();
					}
					else
					{
						if(toMove.tryMove(endX, endY) && !wouldBeCheck(toMove, endX, endY))
						{
							toMove.move(endX, endY);

							if(pawnCanMorph(game.board) != null)
							{
								game.morphPawn(pawnCanMorph(game.board), input);
							}

							game.printBoard();

							if(moveset[4] == 1)
								draw = true;
							else
								draw = false;

							moveType = !moveType;

							Piece[] thisTeam;
							Piece[] opponentTeam;

							if(moveType)
							{
								thisTeam = game.whitePieces;
								opponentTeam = game.blackPieces;
							}
							else
							{
								thisTeam = game.blackPieces;
								opponentTeam = game.whitePieces;
							}

							if(isCheck(thisTeam, opponentTeam))
							{
								if(isCheckmate(thisTeam, opponentTeam))
								{
									if(moveType)
										winner = 0;
									else
										winner = 1;
									System.out.println("Checkmate");
								}
								else
									System.out.println("Check");
							} 
							else {
								Piece king = game.getKing(moveType);
								if(isStalemate(king))
								{
									System.out.println("Stalemate");
									winner = 2;
								}
							}
						}
						else
						{
							System.out.println("Illegal move, try again");
							System.out.println();
						}					
					}
				}
				else
				{
					System.out.println("Illegal move, try again");
					System.out.println();
				}
			}
		}

		//end while loop

		if(winner == 0)
			System.out.println("Black wins!");
		else if(winner == 1)
			System.out.println("White wins!");
		else if(winner == 2)
			System.out.println("Draw");

		in.close();
	}//end playGame()

	/**
	 * Checks if a pawn has made it across the board
	 * @param board board to evaluate
	 */
	static Piece pawnCanMorph(Piece[][] board)
	{
		for(int i = 0; i < 8; i++){
			if(board[0][i] instanceof Pawn && board[0][i].color.equals("white"))
				return board[0][i];
			if(board[7][i] instanceof Pawn && board[7][i].color.equals("black"))
				return board[7][i];
		}
		return null;
	}

	/**
	 * Tests if the input king is in a stalemate
	 * @param king king to check if in stalemate
	 * @return boolean if given king is in a stalemate
	 */
	static boolean isStalemate(Piece king)
	{
		//Ensures 
		if(!king.hasMoved)
		{
			if(king.color.equals("white"))
			{
				if((game.board[king.y - 1][king.x] != null && 
						game.board[king.y - 1][king.x].color.equals(king.color)) &&
						(game.board[king.y - 1][king.x + 1] != null && 
						game.board[king.y - 1][king.x].color.equals(king.color)) &&
						(game.board[king.y - 1][king.x + 1] != null && 
						game.board[king.y - 1][king.x].color.equals(king.color)))
					return false;
			} else
			{
				if((game.board[king.y + 1][king.x] != null && 
						game.board[king.y + 1][king.x].color.equals(king.color)) &&
						(game.board[king.y + 1][king.x + 1] != null && 
						game.board[king.y + 1][king.x].color.equals(king.color)) &&
						(game.board[king.y + 1][king.x + 1] != null && 
						game.board[king.y + 1][king.x].color.equals(king.color)))
					return false;
			}
		}//finish !hasMoved if block

		//The next checks 
		if(king.tryMove(king.x, king.y + 1)
				&& !wouldBeCheck(king, king.x, king.y + 1))
			return false;
		if(king.tryMove(king.x, king.y - 1)
				&& !wouldBeCheck(king, king.x, king.y - 1))
			return false;
		if(king.tryMove(king.x+ 1, king.y)
				&& !wouldBeCheck(king, king.x + 1, king.y))
			return false;
		if(king.tryMove(king.x - 1, king.y)
				&& !wouldBeCheck(king, king.x - 1, king.y))
			return false;
		if(king.tryMove(king.x - 1, king.y + 1)
				&& !wouldBeCheck(king, king.x - 1, king.y + 1))
			return false;
		if(king.tryMove(king.x + 1, king.y + 1)
				&& !wouldBeCheck(king, king.x + 1, king.y + 1))
			return false;
		if(king.tryMove(king.x + 1, king.y - 1)
				&& !wouldBeCheck(king, king.x + 1, king.y - 1))
			return false;
		if(king.tryMove(king.x - 1, king.y - 1)
				&& !wouldBeCheck(king, king.x - 1, king.y - 1))
			return false;
		return true;
	}

	/**
	 * Checks whether teamToCheck is in check to opponentTeam
	 * @param teamToCheck The team to check
	 * @param opponentTeam the other team
	 * @return if in check or not
	 */
	static boolean isCheck(Piece[] teamToCheck, Piece[] opponentTeam)
	{
		return getCheckingPiece(teamToCheck, opponentTeam) != null;
	}

	/**
	 * Returns the opponent piece that is putting current team in check
	 * @param teamToCheck current team
	 * @param opponentTeam other team
	 * @return returns piece putting team in check. else return null
	 */
	private static Piece getCheckingPiece(Piece[] teamToCheck, Piece[] opponentTeam)
	{
		Piece thisKing = teamToCheck[Board.PIECESKINGPOS];

		for(int i = 0; i < 16; i++)
			if(opponentTeam[i] != null && opponentTeam[i].tryMove(thisKing.x, thisKing.y))
				return opponentTeam[i];

		return null;
	}

	/**
	 * Checks if the given piece is moved to x2,y2 then if its team will be in check
	 * @param toMove piece to move
	 * @param x2 x position to move to
	 * @param y2 y position to move to
	 * @return if team is in check if it moves given piece to x2,y2
	 */
	static boolean wouldBeCheck(Piece toMove, int x2, int y2)
	{
		Piece[] teamScenario;
		Piece[] otherTeam;
		if(toMove.color.equals("black"))
		{
			teamScenario = game.blackPieces;
			otherTeam = game.whitePieces;
		}
		else
		{
			teamScenario = game.whitePieces;
			otherTeam = game.blackPieces;
		}

		Piece wasRemoved = game.board[y2][x2];
		int origX = toMove.x;
		int origY = toMove.y;
		boolean hasMovedPrev = toMove.hasMoved;
		boolean hasMoved2Prev = toMove.hasMoved2;

		boolean toReturn;

		Piece rookCastling = null;
		int rookOrigX = 0;
		if(toMove instanceof King && ((King)toMove).canCastle(x2, y2))
		{
			if(toMove.x > x2)
				rookCastling = game.board[y2][0];
			else
				rookCastling = game.board[y2][7];
			rookOrigX = rookCastling.x;
		}

		Piece enpassed = null;
		if(toMove instanceof Pawn)
		{
			if(((Pawn)toMove).canEnpassant(x2, y2))
				enpassed = game.board[toMove.y][x2];
		}

		toMove.move(x2, y2);
		toReturn = isCheck(teamScenario, otherTeam);


		if(rookCastling != null)
		{
			rookCastling.move(rookOrigX, y2);
			rookCastling.hasMoved = false;
		}
		if(enpassed != null)
		{
			game.board[toMove.y][x2] = enpassed;
			int i = 0;
			while(i < 16 && otherTeam[i] != null)
				i++;
			otherTeam[i] = enpassed;
		}



		toMove.move(origX, origY);
		toMove.hasMoved = hasMovedPrev;
		toMove.hasMoved2 = hasMoved2Prev;
		game.board[y2][x2] = wasRemoved;
		if(wasRemoved != null)
		{
			int i = 0;
			while(i < 16 && otherTeam[i] != null)
				i++;
			otherTeam[i] = wasRemoved;
		}
		return toReturn;	
	}

	/**
	 * Checks if teamToCheck is in checkmate
	 * @param teamToCheck team to check if in checkmate
	 * @param opponentTeam other team
	 * @return returns if team to check is in checkmate
	 */
	static boolean isCheckmate(Piece[] teamToCheck, Piece[] opponentTeam)
	{
		Piece king = teamToCheck[Board.PIECESKINGPOS];
		//Checks if king can move to spot that wouldn't be in check
		for(int x = king.x - 1; x < king.x + 2; x++)
			for(int y = king.y - 1; y < king.y + 2; y++)
				if(king.tryMove(x,y) && !wouldBeCheck(king, x, y))
					return false;

		//Checks if any piece can attack piece putting team in check
		Piece attackingPiece = getCheckingPiece(teamToCheck, opponentTeam);
		for(int i = 0; i < 16; i++)
			if(teamToCheck[i] != null)
				if(teamToCheck[i].tryMove(attackingPiece.x, attackingPiece.y) &&
						!wouldBeCheck(teamToCheck[i], attackingPiece.x, attackingPiece.y))
					return false;

		//Checks if a piece can block the attacking piece
		if(attackingPiece instanceof Knight)
			return true;

		int startX = attackingPiece.x;
		int startY = attackingPiece.y;
		int endX = king.x;
		int endY = king.y;
		int incX, incY;

		if(king.x > attackingPiece.x)
			incX = 1;
		else if (king.x < attackingPiece.x)
			incX = -1;
		else
			incX = 0;
		if(king.y > attackingPiece.y)
			incY = 1;
		else if (king.y < attackingPiece.y)
			incY = -1;
		else
			incY = 0;

		int end;
		if(startX != endX)
			end = endX - startX;
		else
			end = endY - startY;

		for(int j = 1; j <= end; j++)
		{
			for(int i = 0; i < 16; i++)

				if(teamToCheck[i] != null)
					if(teamToCheck[i].tryMove(startX + (incX*j), startY + (incY*j)) && 
							!wouldBeCheck(teamToCheck[i], startX + (incX*j), startY + (incY*j)))
					{
						return false;
					}
		}


		return true;
	}

	/**
	 * translateFileRank
	 * Converts file/rank array numbering into the 
	 * numbering convention for arrays in programming
	 * Also checks if there is draw at the end
	 * @param oldFR inputed file/rank
	 * @return int[] appropriate row/column for board and whether draw is called
	 */
	static int[] translateFileRank(String oldFR)
	{
		int[] newFR = new int[5];
		newFR[0] = ((int) oldFR.charAt(0)) - 97;
		newFR[1] = Math.abs(Integer.parseInt(oldFR.charAt(1) + "") - 8);
		newFR[2] = ((int) oldFR.charAt(3)) - 97;
		newFR[3] = Math.abs(Integer.parseInt(oldFR.charAt(4) + "") - 8);

		if(oldFR.endsWith("draw?"))
			newFR[4] = 1;

		return newFR;
	}
}