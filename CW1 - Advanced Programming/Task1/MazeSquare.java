import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class MazeSquare extends GameSquare 
{
    private GameBoard board;                                        // The game board this square belongs to.
    private boolean target;                                         // Indicates if this square is the target square.
    private boolean visited;                                        // Indicates if this square has been visited during pathfinding.
    private MazeSquare parent;                                      // The parent square in the pathfinding process. (not used in my design)
    private Stack<MazeSquare> currentPath = new Stack<>();          // Stack to store the current path.
    private List<MazeSquare> shortestPath = new LinkedList<>();     // List to store the shortest path found.

    public MazeSquare(int x, int y, GameBoard board) 
    {
        super(x, y);
        this.board = board;
    }

    public void leftClicked() 
    {
        /**
         * Iterate over all squares on the board to reset the previous target square.
         */
        for (int x = 0; x < 10; x++) 
        {
            for (int y = 0; y < 10; y++) 
            {
                MazeSquare square = (MazeSquare) board.getSquareAt(x, y);
                if (square.target)                               // Check if this square is the previous target
                {
                    square.target = false;                       // Reset the target flag
                }
            }
        }
        this.target = true;                                      // Set this square as the new target
    }

    public void rightClicked() 
    {
        /**
         * Initialize the shortest path finding process.
         * Add this square as the starting point for the path.
         */
        shortestPath.add(this);
        dfs(this, 1);                                // Begin depth-first search from this square
        if (shortestPath.size() > 1) 
        {
            printPath(shortestPath);                              // If a path is found, print it.
        }
        else 
        {
            System.out.println("No path exists.");              // Indicate if no path was found.
        }
    }
    
    private void dfs(MazeSquare current, int currentDepth) 
    {
        /**
         * Base case. found the target and it is shorter than the shortest path found so far
         */ 
        if (current.target && (shortestPath.size() == 1 || currentDepth < shortestPath.size())) 
        {
            currentPath.push(current);
            shortestPath = new LinkedList<>(currentPath);
            currentPath.pop();
            return;
        }
    
        /**
         * Base case. found the target but the path is not shorter than the shortest path found so far
         */ 
        if (current.target) 
        {
            return;
        }
    
        /** 
         * Recursion. Set the current square as visited and add it to the current path
         */  
        current.visited = true;
        currentPath.push(current);
    
        /** 
         * Recursion. Explore all non-visited neighbors 
         */ 
        MazeSquare[] neighbours = getAccessibleNeighbours(current);
        for (MazeSquare neighbour : neighbours) 
        {
            if (neighbour != null && !neighbour.visited) 
            {
                dfs(neighbour, currentDepth + 1);
            }
        }
    
        /**
         * Backtrack. Remove the current square from the current path when going back.
         */
        currentPath.pop();
        current.visited = false;
    }

    private MazeSquare[] getAccessibleNeighbours(MazeSquare square) 
    {
        MazeSquare[] neighbours = new MazeSquare[4];

        if (!square.getWall(GameSquare.WALL_LEFT)) 
        {
            neighbours[0] = (MazeSquare) board.getSquareAt(square.getXLocation() - 1, square.getYLocation());
        }

        if (!square.getWall(GameSquare.WALL_RIGHT)) 
        {
            neighbours[1] = (MazeSquare) board.getSquareAt(square.getXLocation() + 1, square.getYLocation());
        }

        if (!square.getWall(GameSquare.WALL_TOP)) 
        {
            neighbours[2] = (MazeSquare) board.getSquareAt(square.getXLocation(), square.getYLocation() - 1);
        }

        if (!square.getWall(GameSquare.WALL_BOTTOM)) 
        {
            neighbours[3] = (MazeSquare) board.getSquareAt(square.getXLocation(), square.getYLocation() + 1);
        }

        return neighbours;
    }

    private void printPath(List<MazeSquare> path) 
    {
        /**
         * Output the shortest path from the starting point to the target.
         */
        System.out.println("Shortest path (from source to target):");
        StringBuilder sb = new StringBuilder();
        for (MazeSquare square : path) 
        {
            /**
             * Append the coordinates of each square in the path to string builder.
             */ 
            sb.append("(").append(square.getXLocation()).append(", ").append(square.getYLocation()).append(") -> ");
        }
    
        
        if (sb.length() > 4)                                   // Remove the last arrow from the path in the terminal.
        {
            sb.setLength(sb.length() - 4);
        }
    
        System.out.println(sb.toString());                     // Print the shortest path.

        resetAll();                                            // Reset the state of all squares except those in the shortest path.
    }

    private void resetAll() 
    {
        /**
         * Reset the state of all squares on the board except those part of the shortest path.
         */
        for (int x = 0; x < 10; x++) 
        {
            for (int y = 0; y < 10; y++) 
            {
                MazeSquare square = (MazeSquare) board.getSquareAt(x, y);
                if (square != null && !shortestPath.contains(square)) 
                {
                    square.reset(0);        // Reset the square with a call to reset method.
                }
            }
        }
    }

    public void reset(int placeHolderInteger) 
    {
        /**
         * Reset the state of this square.
         */
        this.visited = false;                                   // Clear the visited flag.
        this.target = false;                                    // Clear the target flag.
        this.currentPath.clear();                               // Clear the current path.
        this.parent = null;                                     // Reset the parent square reference.
    }
}