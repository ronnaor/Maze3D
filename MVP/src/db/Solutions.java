package db;

import java.io.Serializable;
import java.util.ArrayList;

import algorithms.mazeGenarators.Position;
import algorithms.search.Solution;
import db.Positions;


@SuppressWarnings({ "serial", "hiding" })
public class Solutions<Positions> implements Serializable {
	
	ArrayList<Positions> path;
	int numMoves;
	
	public ArrayList<Positions> getPath() {
		return path;
	}

	public void setPath(ArrayList<Positions> path) {
		this.path = path;
	}

	public int getNumMoves() {
		return numMoves;
	}

	public void setNumMoves(int numMoves) {
		this.numMoves = numMoves;
	}

	Solutions(Solution<Position> sol) {
		this.path = sol.getPath();
		for(Position t: sol.getPath())
		{
			Positions p = new Positions(t);
			this.path.add(p);
		}
		numMoves = this.path.size();
	}


}
