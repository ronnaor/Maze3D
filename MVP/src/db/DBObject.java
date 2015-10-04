package db;

import java.io.Serializable;
import algorithms.mazeGenarators.Position;
import algorithms.search.Solution;

@SuppressWarnings("serial")
public class DBObject implements Serializable {
	
	public SimpelingMaze maze;
	public Solution<Position> solution;
	public String name;
	public long mazeID;
	
	public long getMazeID() {
		return mazeID;
	}

	public void setMazeID(long mazeID) {
		this.mazeID = mazeID;
	}

	public Solution<Position> getSolution() {
		return solution;
	}

	public void setSolution(Solution<Position> solution) {
		this.solution = solution;
	}

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
 * @param solution1 the soultion for the maze
 */
	public DBObject(String name1, SimpelingMaze maze1, Solution<Position> solution1)
	{
		this.name = name1;
		this.maze = maze1;
		this.solution =solution1;
	}
/**
 * copy Ctor	
 * @param obj the object we are copying from
 */
	public DBObject(DBObject obj) {
		this.name = obj.getName();
		this.maze = obj.getMaze();
		this.solution = obj.getSolution();
		this.mazeID =obj.getMazeID();
	}

public SimpelingMaze getMaze() {
	return maze;
}

public void setMaze(SimpelingMaze maze) {
	this.maze = maze;
}
}