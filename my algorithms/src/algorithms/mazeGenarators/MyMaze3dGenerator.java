package algorithms.mazeGenarators;

/**
 * MyMaze3dGenerator class extends Maze3d
 *
 */
public class MyMaze3dGenerator extends Maze3d {
	
	/**
	 * implement Maze3d abstract method generate
	 * @param sizeX x axis size
	 * @param sizeY y axis size
	 * @param sizeZ z axis size
	 * @return Maze3d the maze generated
	 */
	@Override
	public Maze3d generate(int sizeX, int sizeY, int sizeZ) {	
		if (sizeX<=0 || sizeY<=0 || sizeZ<=0)
		{
			return null;
		}
		maze = new int [sizeX] [sizeY] [sizeZ]; // init the maze to the selected Size
		for (int i=0; i<sizeX; i++) //init the maze cells to 2 for unvisited cells 
		{
			for (int j=0; j<sizeY; j++)
			{
				for (int k=0; k<sizeZ; k++)
				{
					maze[i][j][k] = 2;  
				}
			}
		}
		startPosition = Position.startGoalPos(sizeX, sizeY, sizeZ); //set startPosition to the maze
		setWallOrPath(startPosition); //build the maze
		goalPosition = Position.startGoalPos(sizeX, sizeY, sizeZ); //set goalPosition to the maze
		int goalX = goalPosition.getX();
		int goalY = goalPosition.getY();
		int goalZ = goalPosition.getZ();
		int startX = startPosition.getX();
		int startY = startPosition.getY();
		int startZ = startPosition.getZ();
		while ( (maze[goalX][goalY][goalZ]==1) || ( (goalX==startX) && (goalY==startY) && (goalZ==startZ) ) ) // check that the goalPosition is valid
		{
			goalPosition = Position.startGoalPos(sizeX, sizeY, sizeZ);
			goalX = goalPosition.getX();
			goalY = goalPosition.getY();
			goalZ = goalPosition.getZ();
		}
		return this;
	}
	/**
	 * returns if this cell will be wall
	 * @param p Position we check
	 * @return true if will be wall and false if not
	 */
	public boolean stillWall(Position p) //returns if this cell will be wall (true) or not (false)
	{
		// get the reference point x y and z
		int pX = p.getX();
		int pY = p.getY();
		int pZ = p.getZ();
		// check for all the options that removing the wall will create loop in the maze  
		if (((pX-1 >= 0) && (pZ-1 >= 0) && (maze[pX-1][pY][pZ]==0) &&(maze[pX-1][pY][pZ-1]==0) &&(maze[pX][pY][pZ-1]==0)) 
				|| ((pX-1 >= 0) && (pZ+1 < maze[0][0].length) && (maze[pX-1][pY][pZ]==0) &&(maze[pX-1][pY][pZ+1]==0) &&(maze[pX][pY][pZ+1]==0))
				|| ((pX+1 < maze.length) && (pZ-1 >= 0) && (maze[pX+1][pY][pZ]==0) &&(maze[pX+1][pY][pZ-1]==0) &&(maze[pX][pY][pZ-1]==0)) 
				|| ((pX+1 < maze.length) && (pZ+1 < maze[0][0].length) && (maze[pX+1][pY][pZ]==0) &&(maze[pX+1][pY][pZ+1]==0) &&(maze[pX][pY][pZ+1]==0))
				|| ((pY+1 < maze[0].length) && (pZ-1 >= 0) && (maze[pX][pY+1][pZ]==0) &&(maze[pX][pY+1][pZ-1]==0) &&(maze[pX][pY][pZ-1]==0)) 
				|| ((pY+1 < maze[0].length) && (pZ+1 < maze[0][0].length) && (maze[pX][pY+1][pZ]==0) &&(maze[pX][pY+1][pZ+1]==0) &&(maze[pX][pY][pZ+1]==0))
				|| ((pY+1 < maze[0].length) && (pX-1 >= 0) && (maze[pX][pY+1][pZ]==0) &&(maze[pX-1][pY+1][pZ]==0) &&(maze[pX-1][pY][pZ]==0)) 
				|| ((pY+1 < maze[0].length) && (pX+1 < maze.length) && (maze[pX][pY+1][pZ]==0) &&(maze[pX+1][pY+1][pZ]==0) &&(maze[pX+1][pY][pZ]==0))
				|| ((pY-1 >= 0) && (pZ-1 >= 0) && (maze[pX][pY-1][pZ]==0) &&(maze[pX][pY-1][pZ-1]==0) &&(maze[pX][pY][pZ-1]==0)) 
				|| ((pY-1 >= 0) && (pZ+1 < maze[0][0].length) && (maze[pX][pY-1][pZ]==0) &&(maze[pX][pY-1][pZ+1]==0) &&(maze[pX][pY][pZ+1]==0))
				|| ((pY-1 >= 0) && (pX-1 >= 0) && (maze[pX][pY-1][pZ]==0) &&(maze[pX-1][pY-1][pZ]==0) &&(maze[pX-1][pY][pZ]==0)) 
				|| ((pY-1 >= 0) && (pX+1 < maze.length) && (maze[pX][pY-1][pZ]==0) &&(maze[pX+1][pY-1][pZ]==0) &&(maze[pX+1][pY][pZ]==0))
				)
		{
			return true;
		}
		return false;
	}
	
	/**
	 *  set for each cell in the maze if it is a wall (1) or not (0)
	 * @param p the Position we check
	 */
	public void setWallOrPath(Position p) // set for each cell in the maze if it is a wall (1) or not (0)
	{
		Position[] unvisitedneighbors = getUnvisitedNeighbors(p);
		int pX = p.getX();
		int pY = p.getY();
		int pZ = p.getZ();
		
		if (stillWall(p))
		{
			maze[pX][pY][pZ]=1;
		}
		else
		{
			maze[pX][pY][pZ]=0;
		}
		
		while (unvisitedneighbors.length>0) // do it for all unvisited cells
		{
			int randUnvisited = rand.nextInt(unvisitedneighbors.length); // get random neighbor and go to it
			setWallOrPath(unvisitedneighbors[randUnvisited]);
			unvisitedneighbors = getUnvisitedNeighbors(p); //check again what neighbors are still unvisited after the recursion 
		}
	}
}
