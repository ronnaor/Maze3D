package db;

import java.io.Serializable;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import java.sql.Blob;
import java.sql.SQLException;

import algorithms.mazeGenarators.Maze3d;

public class SimpelingMaze implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	//for the HQL DB
	private DBObject db;
	private int mazeID = 0;
	
	private Blob simpleMaze;
	
	public SimpelingMaze() {
this.simpleMaze=null;
}
/**
 * converting normal maze to light maze	
 * @param maze Maze3d normal maze
 */
	public SimpelingMaze(Maze3d maze) {
		byte[] e = maze.toByteArray();
		try {
			this.simpleMaze = new SerialBlob(e);
		} catch (SerialException e1) {
			System.out.println("can't enter maze to serial blob");
			e1.printStackTrace();
		} catch (SQLException e1) {
			System.out.println("can't enter maze to db blob");
			e1.printStackTrace();
		}
	}


public Blob getSimpleMaze() {
	return simpleMaze;
}
public void setSimpleMaze(Blob simpleMaze) {
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
public byte[] getSimpleMazeArray() {
	byte[] maze = null;
	try {
		int blobLength = (int) this.simpleMaze.length();  
		maze = this.simpleMaze.getBytes(1, blobLength);
	} catch (SQLException e) {
		System.out.println("can't get blob");
		e.printStackTrace();
	}
	return maze;
}
}
