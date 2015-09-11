package algorithms.mazeGenarators;

/**
 * Maze3dGenerator interface
 */
public interface Maze3dGenerator {
	/**
	 * get the time it takes to do the algorithm 
	 * @param sizeX x axis size
	 * @param sizeY y axis size
	 * @param sizeZ z axis size
	 * @return time it takes to do the algorithm 
	 */
	String measureAlgorithmTime(int sizeX, int sizeY, int sizeZ);

	/**
	 * generate a maze
	 * @param sizeX x axis size
	 * @param sizeY y axis size
	 * @param sizeZ z axis size
	 * @return the maze generated
	 */
	Maze3d generate(int sizeX, int sizeY, int sizeZ);

}
