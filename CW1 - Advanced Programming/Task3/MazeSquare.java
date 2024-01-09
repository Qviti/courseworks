import java.util.LinkedList;
import java.util.List;

public class MazeSquare extends GameSquare 
{
    private GameBoard board;                                                        // The game board this square belongs to.
    private boolean target;                                                         // Indicates if this square is the target square.
    private boolean visited;                                                        // Indicates if this square has been visited during pathfinding.
    private List<List<MazeSquare>> allShortestPaths = new LinkedList<>();           // Store all shortest paths
    private int shortestPathLength = Integer.MAX_VALUE;                             // Store the length of the shortest path


    
    public MazeSquare(int x, int y, GameBoard board) {
        super(x, y);
        this.board = board;
    }

    public void leftClicked() 
    {
        this.target = true;                                                         // Set this square as the new target
    }

    public void rightClicked() 
    {
        List<MazeSquare> currentPath = new LinkedList<>();
        currentPath.add(this);                                                      // Add this square as the starting point for the path.
        
        // First DFS to find the shortest path length
        findShortestPathLength(this, currentPath, 0);
        
        // Second DFS to find all shortest paths
        findAllShortestPaths(this, currentPath, 0);
        
        // Print all shortest paths
        if (!allShortestPaths.isEmpty()) {
            for (List<MazeSquare> path : allShortestPaths) 
            {
                printPath(path);
            }
        } else {
            System.out.println("No path exists.");
        }

        // Reset for another search
        resetAll();
    }

    /**
     * Recursive method that uses depth-first Search to find the length.
     */
    private void findShortestPathLength(MazeSquare current, List<MazeSquare> currentPath, int depth) 
    {
        if (current.target) 
        {
            if (depth < shortestPathLength) 
            {
                shortestPathLength = depth;
            }
            return;
        }

        current.visited = true;

        MazeSquare[] neighbors = getAccessibleNeighbours(current);
        for (MazeSquare neighbor : neighbors) 
        {
            if (neighbor != null && !neighbor.visited) 
            {
                findShortestPathLength(neighbor, currentPath, depth + 1);
            }
        }

        current.visited = false;
    }
     /**
     * Recursive method that uses depth-first Search to find the all shortest paths.
     */
    private void findAllShortestPaths(MazeSquare current, List<MazeSquare> currentPath, int depth) 
    {
        if (current.target && depth == shortestPathLength) 
        {
            allShortestPaths.add(new LinkedList<>(currentPath));
            return;
        }

        current.visited = true;

        MazeSquare[] neighbors = getAccessibleNeighbours(current);
        for (MazeSquare neighbor : neighbors) 
        {
            if (neighbor != null && !neighbor.visited) 
            {
                currentPath.add(neighbor);
                findAllShortestPaths(neighbor, currentPath, depth + 1);
                currentPath.remove(currentPath.size() - 1);                            // Backtrack
            }
        }

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
    /**
     * Output the shortest paths.
     */
    {           
        StringBuilder sb = new StringBuilder();
        for (MazeSquare square : path) 
        {
            sb.append("(").append(square.getXLocation()).append(", ").append(square.getYLocation()).append(") -> ");
        }

        if (sb.length() > 4) 
        {
            sb.setLength(sb.length() - 4);                                             // Remove the last arrow
        }

        System.out.println(sb.toString());                                             // Print the path
    }

    private void resetAll() 
    /**
     * Reset the state of all squares on the board
     */
    {
        for (int x = 0; x < 10; x++) 
        {
            for (int y = 0; y < 10; y++) 
            {
                MazeSquare square = (MazeSquare) board.getSquareAt(x, y);
                if (square != null) 
                {
                    square.reset(0);
                }
            }
        }
        allShortestPaths.clear();                                                      // Clear all recorded paths
    }

    public void reset(int placeHolderInteger) 
    /**
    * Reset the state of this square.
    */
    {
        this.visited = false;
        this.target = false;
    }
}