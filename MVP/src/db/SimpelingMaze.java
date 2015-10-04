package db;

import java.io.Serializable;
import java.util.List;

import algorithms.mazeGenarators.Maze3d;

@SuppressWarnings("serial")
public class SimpelingMaze implements Serializable{
	
	public List<Byte> simpleMaze;
/**
 * converting normal maze to light maze	
 * @param maze Maze3d normal maze
 */
	public SimpelingMaze(Maze3d maze) {
		for (Byte b : maze.toByteArray())
		this.simpleMaze.add(b);
	}

	public List<Byte> getSimpleMaze() {
		return simpleMaze;
	}

	public void setSimpleMaze(List<Byte> simpleMaze) {
		this.simpleMaze = simpleMaze;
	}
/**
 * copy Ctor
 * @param maze the byte maze that being copied
 */
	public SimpelingMaze(SimpelingMaze maze) {
		this.simpleMaze=maze.getSimpleMaze();
	}
}
