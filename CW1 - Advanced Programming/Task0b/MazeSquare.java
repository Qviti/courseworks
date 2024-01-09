public class MazeSquare extends GameSquare
{
	private GameBoard board;			// A reference to the GameBoard this square is part of.
	private boolean target;				// true if this square is the target of the search.

	/**
	 * Create a new GameSquare, which can be placed on a GameBoard.
	 * 
	 * @param x the x co-ordinate of this square on the game board.
	 * @param y the y co-ordinate of this square on the game board.
	 * @param board the GameBoard that this square resides on.
	 */
	public MazeSquare(int x, int y, GameBoard board)
	{
		super(x, y);
		this.board = board;
	}

	/**
	 * A method that is invoked when a user clicks on this square.
	 * This defines the end point for the search.
	 * When a square is left-clicked, computes the coordinates of its right neighbor 
     * If no right neighbor exists, it catches the exception and prints "No square to the right."
	 */	
    public void leftClicked()
	{
		try {
			// Attempt to get the square to the right of the clicked one
			MazeSquare rightSquare = (MazeSquare) board.getSquareAt(getXLocation() + 1, getYLocation());

			// If successful, print its coordinates
			rightSquare.print();
			} 
			catch (Exception e)
			{
			// If no square is to the right of this one, notify the user
			System.out.println("No square to the right.");
			}
	}
    
    /**
	 * A method that is invoked when a user clicks on this square.
	 * This defines the start point for the search. 
	 */	
	public void rightClicked()
	{
	}

	/**
	 * A method that is invoked when a reset() method is called on GameBoard.
	 * 
	 * @param n An unspecified value that matches that provided in the call to GameBoard reset()
	 */
	public void reset(int n)
	{

	}

	/**
 	* Prints the x and y coordinates of the square in the console (if it exists).
 	*/	
	void print()
	{
		System.out.println("Square coordinates: (" + getXLocation() + ", " + getYLocation() + ")");
	}
}