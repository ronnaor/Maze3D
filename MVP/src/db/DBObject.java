package db;

import java.io.Serializable;
import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Maze3dByteArr;
import algorithms.mazeGenarators.Position;
import algorithms.search.Solution;


public class DBObject implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	private SimpelingMaze maze;
	private Solutions solution;
	private String name;
	private int mazeID = 0;
	



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

/**
 * Ctor getting row data	
 * @param name1 the name of the maze
 * @param maze1 the maze itself
 * @param solution1 the solution for the maze
 */
	public DBObject(String name1, SimpelingMaze maze1, Solutions solution1)
	{
		this.name = name1;
		this.maze=maze1;
		this.solution=solution1;
		
	}
/**
 * copy Ctor	
 * @param obj the object we are copying from
 */
	public DBObject(DBObject obj) {
		this.name = obj.getName();
		this.maze = obj.getMaze();
		this.solution = obj.getSolution();
		this.setMazeID(obj.getMazeID());
	}


public Maze3d getFixMaze(){
	Maze3d fix=new Maze3dByteArr(this.maze.getSimpleMazeArray());
	return fix;
}

public Solution<Position> getFixSolution(){
	Solution<Position> fix = new Solution<Position>(this.solution.getSolution());
	return fix;
}

public SimpelingMaze getMaze() {
	return maze;
}

public void setMaze(SimpelingMaze maze) {
	this.maze = maze;
}

public Solutions getSolution() {
	return solution;
}

public void setSolution(Solutions solution) {
	this.solution = solution;
}

public int getMazeID() {
	return mazeID;
}

public void setMazeID(int mazeID) {
	this.mazeID = mazeID;
}

}
