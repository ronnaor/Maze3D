package algorithms.search;

import org.junit.Assert;
import org.junit.Test;

import algorithms.mazeGenarators.Position;
import algorithms.mazeGenarators.SimpleMaze3dGenerator;

/**
 * class of tests for AStar 
 *
 */
public class AStarTest {

	@Test
	public void test() {
		Maze3DSearchable mazeSearchableNull = new Maze3DSearchable(null); //create Maze3DSearchable that gets no maze
		Maze3DSearchable mazeSearchable = new Maze3DSearchable(new SimpleMaze3dGenerator().generate(3, 4, 6)); ////create Maze3DSearchable 
		
		AStar<Position> AStarNull = new AStar<Position>(null); // create AStar algorithm with no heuristic 
		
		Assert.assertNull(AStarNull.search(null)); //Check for errors in  AStarNull search method that gets no searchable
		Assert.assertNull(AStarNull.search(mazeSearchableNull)); //Check for errors in  AStarNull search method that gets mazeSearchableNull
		Assert.assertNull(AStarNull.search(mazeSearchable)); //Check for errors in  AStarNull search method that gets mazeSearchable
		
		AStar<Position> AStarAir = new AStar<Position>(new MazeAirDistance()); // create AStar algorithm with AirDistance heuristic 
		
		Assert.assertNull(AStarAir.search(null)); //Check for errors in  AStarAir search method that gets no searchable
		Assert.assertNull(AStarAir.search(mazeSearchableNull)); //Check for errors in  AStarAir search method that gets mazeSearchableNull
		Assert.assertNotNull(AStarAir.search(mazeSearchable)); //Check for errors in  AStarAir search method that gets mazeSearchable
		
		AStar<Position> AStarAirManhatten = new AStar<Position>(new MazeManhattenDistance()); // create AStar algorithm with ManhattenDistance heuristic 
		
		Assert.assertNull(AStarAirManhatten.search(null)); //Check for errors in  AStarAirManhatten search method that gets no searchable
		Assert.assertNull(AStarAirManhatten.search(mazeSearchableNull)); //Check for errors in  AStarAirManhatten search method that gets mazeSearchableNull
		Assert.assertNotNull(AStarAirManhatten.search(mazeSearchable)); //Check for errors in  AStarAirManhatten search method that gets mazeSearchable
	}

	/*public static void main(String[] args) {
		AStarTest a = new AStarTest();
		a.test();
	}*/
}
