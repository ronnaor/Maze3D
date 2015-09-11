package algorithms.mazeGenarators;

import java.util.Random;
/**
 *  Maze3d abstract class implements Maze3dGenerator
 *
 */
public abstract class Maze3d implements Maze3dGenerator{
	protected static Random rand = new Random();
	protected Position startPosition;
	protected Position goalPosition;
	protected int [] [] [] maze;
	
	/**
	 * default CTOR 
	 */
	public Maze3d() { //default constructor 
		
	}
	
	/**
	 * copy CTOR
	 * @param m the maze we copy
	 */
	public Maze3d(Maze3d m) {   // copy constructor
		startPosition = m.getStartPosition();
		goalPosition = m.getGoalPosition();
		maze = m.getMaze().clone();
	}
	
	/**
	 * Override Maze3dGenerator method measureAlgorithmTime
	 * @param sizeX int the size of x axis
	 * @param sizeY int the size of y axis
	 * @param sizeZ int the size of z axis
	 * @return  String the time it takes the algorithm to run in  milliseconds
	 */
	@Override
	public String measureAlgorithmTime(int sizeX, int sizeY, int sizeZ) {
		long startTime  = System.currentTimeMillis();  // get the time before the beginning of generating the maze
		this.generate(sizeX,sizeY,sizeZ); // generate the maze
		long totalTime = (System.currentTimeMillis() - startTime)  ; //get the time it took to generate the maze in seconds
		String time = "The time it takes the algorithm to run in  milliseconds is: " + totalTime;
		return time;
	}
	/**
	 * Override Maze3dGenerator method generate (abstract)
	 */
	@Override
	public abstract Maze3d generate(int sizeX, int sizeY, int sizeZ);
	
	/**
	 * getter for maze
	 * @return int[][][] maze
	 */
	public int[][][] getMaze() {
		return maze;
	}

	/**
	 * getter for goalPosition
	 * @return Position goalPosition
	 */
	public Position getGoalPosition() {
		return goalPosition;
	}
	/**
	 * getter for startPosition
	 * @return Position startPosition
	 */
	public Position getStartPosition() {
		return startPosition;
	}
	/**
	 * get possible moves from a position
	 * @param p the position we check
	 * @return String[] of the posible moves
	 */
	public String[] getPossibleMoves(Position p) {
		String [] allArr=new String[6]; //array that contains all the moves (possible and not possible) 
		int cnt = 0;
		// get the reference point x y and z
		int pX = p.getX();
		int pY = p.getY();
		int pZ = p.getZ();
		
		if ((pX-1 >= 0) && (maze[pX-1][pY][pZ]==0)) //Checks whether it is possible to move left
		{
			allArr[0] = "Left";
			cnt++;
		}
		if ((pX+1 < maze.length) && (maze[pX+1][pY][pZ]==0)) //Checks whether it is possible to move Right
		{
			allArr[1] = "Right";
			cnt++;
		}
		if ((pY-1 >= 0) && (maze[pX][pY-1][pZ]==0)) //Checks whether it is possible to move Down
		{
			allArr[2] = "Down";
			cnt++;
		}
		if ((pY+1 < maze[0].length) && (maze[pX][pY+1][pZ]==0)) //Checks whether it is possible to move Up
		{
			allArr[3] = "Up";
			cnt++;
		}
		if ((pZ-1 >= 0) && (maze[pX][pY][pZ-1]==0)) //Checks whether it is possible to move Back
		{
			allArr[4] = "Backward";
			cnt++;
		}
		if ( (pZ+1 < maze[0][0].length) && (maze[pX][pY][pZ+1]==0)) //Checks whether it is possible to move Forward
		{
			allArr[5] = "Forward";
			cnt++;
		}
		String [] posArr=new String[cnt]; //array that contains only the possible moves
		int cnt2=0;
		for (int i=0; i<6; i++)
		{
			if (allArr[i] != null)
			{
				posArr[cnt2] = allArr[i];
				cnt2++;
			}
		}
		return posArr;
	}
	
	/**
	 * get the maze by section x
	 * @param x the index of x axis we want to get 2D maze by
	 * @return int[][] 2D maze in the x we selected
	 * @throws IndexOutOfBoundsException if x is out of the maze range
	 */
	public int[][] getCrossSectionByX(int x) throws IndexOutOfBoundsException {
		if (x<0 || x>=maze.length)
		{
			throw new IndexOutOfBoundsException();
		}
		int[][] tempArr = new int[maze[0].length][maze[0][0].length];
		for(int j=0; j<maze[0].length; j++)
		{
			for(int k=0; k<maze[0][0].length; k++)
				tempArr[j][k] = maze[x][j][k];
		}
		return tempArr;
	}
	
	/**
	 * get the maze by section y
	 * @param y the index of y axis we want to get 2D maze by
	 * @return int[][] 2D maze in the y we selected
	 * @throws IndexOutOfBoundsException if y is out of the maze range
	 */
	public int[][] getCrossSectionByY(int y) throws IndexOutOfBoundsException {
		if (y<0 || y>=maze[0].length)
		{
			throw new IndexOutOfBoundsException();
		}
		int[][] tempArr = new int[maze.length][maze[0][0].length];
		for(int i=0; i<maze.length; i++)
		{
			for(int k=0; k<maze[0][0].length; k++)
				tempArr[i][k] = maze[i][y][k];
		}
		return tempArr;
	}
	
	/**
	 * get the maze by section z
	 * @param z the index of z axis we want to get 2D maze by
	 * @return int[][] 2D maze in the z we selected
	 * @throws IndexOutOfBoundsException if z is out of the maze range
	 */
	public int[][] getCrossSectionByZ(int z) throws IndexOutOfBoundsException {
		if (z<0 || z>=maze[0][0].length)
		{
			throw new IndexOutOfBoundsException();
		}
		int[][] tempArr = new int[maze.length][maze[0].length];
		for(int i=0; i<maze.length; i++)
		{
			for(int j=0; j<maze[0].length; j++)
				tempArr[i][j] = maze[i][j][z];
		}
		return tempArr;
	}
	
	/**
	 * return the cell unvisited Neighbors cells
	 * @param p Position we look for unvisited Neighbors
	 * @return Position[] array, the unvisited Neighbors of p
	 */
	public Position[] getUnvisitedNeighbors (Position p) //return the cell unvisited (2) Neighbors cells
	{
		Position [] allArr=new Position[6]; //array that contains all unvisited (2) Neighbors if visited has NULL
		int cnt = 0;
		// get the reference point x y and z
		int pX = p.getX();
		int pY = p.getY();
		int pZ = p.getZ();
		
		if ((pX-1 >= 0) && (maze[pX-1][pY][pZ]==2)) //Checks whether the left cell is unvisited
		{
			allArr[0] = new Position(pX-1,pY,pZ);
			cnt++;
		}
		if ((pX+1 < maze.length) && (maze[pX+1][pY][pZ]==2)) //Checks whether  Right cell is unvisited
		{
			allArr[1] = new Position(pX+1,pY,pZ);
			cnt++;
		}
		if ((pY-1 >= 0) && (maze[pX][pY-1][pZ]==2)) //Checks whether Down cell is unvisited
		{
			allArr[2] = new Position(pX,pY-1,pZ);
			cnt++;
		}
		if ((pY+1 < maze[0].length) && (maze[pX][pY+1][pZ]==2)) //Checks whether Up cell is unvisited
		{
			allArr[3] = new Position(pX,pY+1,pZ);
			cnt++;
		}
		if ((pZ-1 >= 0) && (maze[pX][pY][pZ-1]==2)) //Checks whether Back cell is unvisited
		{
			allArr[4] = new Position(pX,pY,pZ-1);
			cnt++;
		}
		if ( (pZ+1 < maze[0][0].length) && (maze[pX][pY][pZ+1]==2)) //Checks whether Forward cell is unvisited
		{
			allArr[5] = new Position(pX,pY,pZ+1);
			cnt++;
		}
		Position [] posArr=new Position[cnt]; //array that contains only the unvisited cells
		int cnt2=0;
		for (int i=0; i<6; i++)
		{
			if (allArr[i] != null)
			{
				posArr[cnt2] = allArr[i];
				cnt2++;
			}
		}
		return posArr;
	}
	
}
