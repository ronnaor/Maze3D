package algorithms.search;

import algorithms.mazeGenarators.Position;

/**
 * MazeAirDistance class implements Heuristic<T> with type Position
 *
 */
public class MazeAirDistance implements Heuristic<Position> {

	/**
	 * Override for Heuristic method h
	 * @param state the checked state
	 * @param goal the goal state 
	 * @return the sqrt of the sum of distance^2 in all axis
	 */
	@Override
	public double h(State<Position> state, State<Position> goal) {
		double xAxis = (double)Math.abs(state.getState().getX()-goal.getState().getX());
		double yAxis = (double)Math.abs(state.getState().getY()-goal.getState().getY());
		double zAxis = (double)Math.abs(state.getState().getZ()-goal.getState().getZ());
		return Math.sqrt(Math.pow(xAxis, 2)+Math.pow(yAxis, 2)+Math.pow(zAxis, 2));
	}

}
