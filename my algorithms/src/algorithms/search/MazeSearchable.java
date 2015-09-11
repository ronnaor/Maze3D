package algorithms.search;

import java.util.ArrayList;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;

/**
 * 
 * class MazeSearchable that implements Searchable<T> when T is Position 
 * 
 */
public class MazeSearchable implements Searchable<Position> {

	private Maze3d maze;
	private double cost;
	
	/**
	 * CTOR
	 * @param maze Maze3d maze
	 * @param cost double cost
	 */
	public MazeSearchable(Maze3d maze, double cost) { // CTOR 
		this.maze = maze;
		this.cost = cost;
	}
	/**
	 * copy CTOR 
	 * @param maze MazeSearchable that we copy
	 */
	public MazeSearchable(MazeSearchable maze) { // copy CTOR 
		this.maze = maze.getMaze();
		this.cost = maze.getCost();
	}
	
	//getters and setters
	/**
	 * getter for maze
	 * @return Maze3d maze
	 */
	public Maze3d getMaze() {
		return maze;
	}
	/**
	 * setter for maze
	 * @param maze Maze3d maze
	 */
	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}
	/**
	 * getter for cost
	 * @return double cost of a move
	 */
	public double getCost() {
		return cost;
	}
	/**
	 * setter for cost
	 * @param cost double cost of a move
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * Override of Searchable method getStartState
	 * @return State<Position> the start position 
	 */
	@Override
	public State<Position> getStartState() { //return state of start position
		return new State<Position>(maze.getStartPosition(), cost);
	}
	/**
	 * Override of Searchable method getGoalState
	 * @return State<Position> the goal position 
	 */
	@Override
	public State<Position> getGoalState() { //return state of goal position
		return new State<Position>(maze.getGoalPosition(), cost);
	}
	/**
	 * Override of Searchable method getAllPossibleStates
	 * @return ArrayList of all the possible moves 
	 */
	@Override
	public ArrayList<State<Position>> getAllPossibleStates(State<Position> state) { //return states of all position moves from a position in the maze

		ArrayList<State<Position>> possibleStates = new ArrayList<State<Position>>();
		Position pos = state.getState();
		String[] possiblePos = new String[maze.getPossibleMoves(pos).length];
		possiblePos = maze.getPossibleMoves(pos);
		for (String str : possiblePos)
		{
			if (str.equals("Left"))
				possibleStates.add(new State<Position>(new Position(pos.getX()-1,pos.getY(),pos.getZ()), cost));
			if (str.equals("Right"))
				possibleStates.add(new State<Position>(new Position(pos.getX()+1,pos.getY(),pos.getZ()), cost));
			if (str.equals("Down"))
				possibleStates.add(new State<Position>(new Position(pos.getX(),pos.getY()-1,pos.getZ()), cost));
			if (str.equals("Up"))
				possibleStates.add(new State<Position>(new Position(pos.getX(),pos.getY()+1,pos.getZ()), cost));
			if (str.equals("Backward"))
				possibleStates.add(new State<Position>(new Position(pos.getX(),pos.getY(),pos.getZ()-1), cost));
			if (str.equals("Forward"))
				possibleStates.add(new State<Position>(new Position(pos.getX(),pos.getY(),pos.getZ()+1), cost));				
		}
		return possibleStates;
	}

}
