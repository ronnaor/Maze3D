package algorithms.search;

/**
 * Heuristic  generic interface
 * @param <T> the type of we will work with 
 */
public interface Heuristic<T> {
	/**
	 * h method finding an approximate cost from where we are to the goal
	 * @param state the checked state
	 * @param goal the goal state
	 * @return approximate cost from state to the goal
	 */
	public double h(State<T> state, State<T> goal);

}
