package db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import algorithms.mazeGenarators.Maze3d;

public class SimpelingMaze implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	//for the HQL DB
	private DBObject db;
	private int mazeID = 0;
	
	private List<Byte> simpleMaze;
	
	public SimpelingMaze() {
this.simpleMaze=null;
}
/**
 * converting normal maze to light maze	
 * @param maze Maze3d normal maze
 */
	public SimpelingMaze(Maze3d maze) {
		simpleMaze = new ArrayList<Byte>();
		byte[] e = maze.toByteArray();
		
		for (int i=0; i<e.length; i++)
		{
			this.simpleMaze.add(e[i]);
		}
		
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

/*public DBObject getDb() {
	return db;
}

public void setDb(DBObject db) {
	this.db = db;
}*/

public byte[] getSimpleMazeArray() {
	byte[] arr = new byte [this.simpleMaze.size()];
	int cnt =0;
	for (byte b : simpleMaze)
	{
		arr[cnt] = b;
		cnt++;
	}
	return arr;
}

public int getMazeID() {
	return mazeID;
}

public void setMazeID(int mazeID) {
	this.mazeID = mazeID;
}

public DBObject getDb() {
	return db;
}

public void setDb(DBObject db) {
	this.db = db;
}
}
