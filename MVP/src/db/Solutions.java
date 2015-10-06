package db;

import java.io.Serializable;
import java.util.ArrayList;

import algorithms.mazeGenarators.Position;
import algorithms.search.Solution;
import db.Positions;



public class Solutions implements Serializable {

	private static final long serialVersionUID = 1L;
	//for the HQL DB
	private DBObject obj;
	private int mazeID;
	
	private ArrayList<Positions> path;
	private int numMoves;
	
	/*public ArrayList<Positions> getPath() {
		return path;
	}

	public void setPath(ArrayList<Positions> path) {
		this.path = path;
	}*/

	public int getNumMoves() {
		return numMoves;
	}

	public void setNumMoves(int numMoves) {
		this.numMoves = numMoves;
	}

	public Solutions(Solution<Position> sol) {
		ArrayList<Position> temp = sol.getPath();
		path = new ArrayList<Positions>();
		for(Position t: temp)
		{
			Positions p = new Positions(t);
			this.path.add(p);
		}
		numMoves = this.path.size();
	}

/*	public DBObject getObj() {
		return obj;
	}

	public void setObj(DBObject obj) {
		this.obj = obj;
	}*/

	public Solution<Position> getSolution() {
		ArrayList<Position> p = new ArrayList<Position>();
		for(Positions t: this.path)
		{
			Position temp = new Position(t.getX(),t.getY(),t.getZ());
			p.add(temp);
		}
		Solution<Position> fix = new Solution<Position>(p);
		return fix;
	}

	public int getMazeID() {
		return mazeID;
	}

	public void setMazeID(int mazeID) {
		this.mazeID = mazeID;
	}

}
