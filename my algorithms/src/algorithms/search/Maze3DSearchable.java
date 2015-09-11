package algorithms.search;

import java.util.ArrayList;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;

public class Maze3DSearchable implements Searchable<Position> {

	private Maze3d maze;
	private double cost;
	
	public Maze3DSearchable(Maze3d m) {
		maze = m;
		cost = 1;
	}
	
	@Override
	public State<Position> getStartState() {
		State<Position>start = new State<Position>(maze.getStartPosition(),cost);
		return start;
	}

	@Override
	public State<Position> getGoalState() {
		State<Position>goal = new State<Position>(maze.getGoalPosition(),cost);
		return goal;
	}

	@Override
	public ArrayList<State<Position>> getAllPossibleStates(State<Position> s) {
		
		ArrayList<State<Position>> possibleStates = new ArrayList<State<Position>>();
		Position b = s.getState();
		String[] p = new String[maze.getPossibleMoves(b).length];
		p = maze.getPossibleMoves(b);
		for (String a : p)
		{
			if (a.equals("Right"))
				possibleStates.add(new State<Position>(new Position(b.getX()+1,b.getY(),b.getZ()),cost));
			if (a.equals("Left"))
				possibleStates.add(new State<Position>(new Position(b.getX()-1,b.getY(),b.getZ()),cost));
			if (a.equals("Up"))
				possibleStates.add(new State<Position>(new Position(b.getX(),b.getY()+1,b.getZ()),cost));
			if (a.equals("Down"))
				possibleStates.add(new State<Position>(new Position(b.getX(),b.getY()-1,b.getZ()),cost));
			if (a.equals("Forword"))
				possibleStates.add(new State<Position>(new Position(b.getX(),b.getY(),b.getZ()+1),cost));
			if (a.equals("Back"))
				possibleStates.add(new State<Position>(new Position(b.getX(),b.getY(),b.getZ()-1),cost));				
		}
		return possibleStates;
	}

}
