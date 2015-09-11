package algorithms.search;

import java.util.ArrayList;

/**
 * generic interface Searchable
 * @param <T> the type of we will work with 
 */
public interface Searchable<T> {
		/**
		 * get the start state of the problem
		 * @return the start state
		 */
		State<T> getStartState();
		/**
		 * get the goal state of the problem
		 * @return the goal state
		 */
		State<T> getGoalState();
		/**
		 * get all the possible moves from a State
		 * @param state the state we check
		 * @return ArrayList of states that we can move to from the state we are cheaking
		 */
	    ArrayList<State<T>> getAllPossibleStates(State<T> state);

}