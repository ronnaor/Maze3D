package algorithms.mazeGenarators;

/**
 * SimpleMaze3dGenerator class extends Maze3d
 *
 */
public class SimpleMaze3dGenerator extends Maze3d {
	
	/**
	 * implement Maze3d abstract method generate
	 * @param sizeX x axis size
	 * @param sizeY y axis size
	 * @param sizeZ z axis size
	 * @return Maze3d the maze generated
	 */
	@Override 
	public Maze3d generate(int sizeX, int sizeY, int sizeZ) {
		
		if (sizeX<=0 || sizeY<=0 || sizeZ<=0 || ((sizeX==1) && (sizeY==1) && (sizeZ==1)))
		{
			return null;
		}
		
		
		maze = new int [sizeX] [sizeY] [sizeZ]; // init the maze to the selected Size
		
		int posx = rand.nextInt(sizeX);
		int posz = rand.nextInt(sizeZ);
		startPosition = new Position(posx, 0, posz); //set start position
		goalPosition = new Position(posx, sizeY-1, posz); //set goal position
		
		for (int i=0; i<sizeX; i++) //create random 1 and 0 on each cell in the maze
		{
			for (int j=0; j<sizeY; j++)
			{
				for (int k=0; k<sizeZ; k++)
				{
					maze[i][j][k] = rand.nextInt(2);  
				}
			}
		}
		
		for (int i=0; i<sizeY; i++)
		{
			maze[posx][i][posz] = 0; // create a route between the start position to the goal position
		}
		
		
		return this;
	}

}
