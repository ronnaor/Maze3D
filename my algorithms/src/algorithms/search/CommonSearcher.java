package algorithms.search;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * CommonSearcher abstract class implements Searcher
 * @param <T> the type of class we will work with
 */
public abstract class CommonSearcher<T> implements Searcher<T> {

	private int evaluatedNodes;
	protected PriorityQueue<State<T>> openList;

	/**
	 * Default CTOR
	 * 
	 */
	 public CommonSearcher() { //CTOR
		  openList=new PriorityQueue<State<T>>();
		  evaluatedNodes=0;
	 }
	 /**
	  * copy CTOR
	  * @param cS CommonSearcher<T> we copy
	  */
	 public CommonSearcher(CommonSearcher<T> cS) { // copy CTOR
		 openList=new PriorityQueue<State<T>>();
		 for (State<T> state : cS.getOpenList())
		 {
			 this.openList.add(state);
		 }
		 this.evaluatedNodes=cS.getNumberOfNodesEvaluated();
	 }

	 /**
	  * Override Searcher method search (abstract)
	  */
	@Override
	public abstract Solution<T> search(Searchable<T> s);
	 /**
     * Override Searcher method getNumberOfNodesEvaluated
     * @return the number of nodes evaluated
     */
	@Override
	public int getNumberOfNodesEvaluated() {
		return evaluatedNodes;
	}
	/**
	 * getter for openList
	 * @return PriorityQueue openList
	 */
	public PriorityQueue<State<T>> getOpenList() {
		return openList;
	}
	/**
	 * setter for openList
	 * @param openList PriorityQueue<T>
	 */
	public void setOpenList(PriorityQueue<State<T>> openList) {
		this.openList = openList;
	}
	
	/**
	 * protected method popOpenList
	 * pop item from openList and add 1 to evaluatedNodes
	 * @return State<T> the item we pop from the list
	 */
	protected State<T> popOpenList() {
		evaluatedNodes++;
		return openList.poll();
	}
	/**
	 * setter for evaluatedNodes
	 * @param evaluatedNodes int parameter
	 */
	public void setEvaluatedNodes(int evaluatedNodes) {
		this.evaluatedNodes = evaluatedNodes;
	}
	/**
	 * protected method addToOpenList
	 * @param state State <T> we add to openList
	 */
	protected void addToOpenList(State <T> state){ //add state to openList
		openList.add(state);
	}
	/**
	 * protected method backTrace
	 * @param goalState State<T> the goal state of the problem
	 * @param startState State<T> the start state of the problem
	 * @return Solution<T> the Solution of the problem
	 */
	protected Solution<T> backTrace(State<T> goalState, State<T> startState) { //get an array of all the moves for the solution and return as Solution
		ArrayList<T> pathArr = new ArrayList<T>();
		State<T> state = goalState;
		while (!(state.equals(startState)))
		{
			pathArr.add(state.getState());
			state = state.getCameFrom();
		}
		pathArr.add(startState.getState());
		return new Solution<T>(pathArr);
	}
	/**
	 * check if a State is in openList
	 * @param state State<T> to check
	 * @return true if in and false if not
	 */
	protected boolean openListContains(State<T> state) {	//check if state is in openList		
		return openList.contains(state);
	}
}
