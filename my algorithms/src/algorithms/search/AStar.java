package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * AStar generic class extends CommonSearcher<T>
 * @param <T> the type of class we will work with
 */
public class AStar<T> extends CommonSearcher<T> {
	Heuristic<T> heuristic;
	/**
	 * CTOR
	 * @param h Heuristic<T> type of heuristic we will use
	 */
	public AStar(Heuristic<T> h) { //CTOR
		this.heuristic = h;
	}
	/**
	 * copy CTOR
	 * @param aS AStar<T> we copy 
	 */
	 public AStar(AStar<T> aS) { // copy CTOR
		 super((CommonSearcher<T>)aS);
		 this.heuristic = aS.getHeuristic();
	}
	 
	/**
	 * getter for heuristic
	 * @return heuristic type we use
	 */
	public Heuristic<T> getHeuristic() {
		return heuristic;
	}
	/**
	 * setter for heuristic
	 * @param heuristic heuristic type we will use
	 */
	public void setHeuristic(Heuristic<T> heuristic) {
		this.heuristic = heuristic;
	}
	/**
	 * Override Searcher method search (implement abstract method from CommonSearcher)
	 * @param s Searchable<T> type 
	 * @return Solution to the problem 
	 */
	@Override
	public Solution<T> search(Searchable<T> s)
	{
		double temp;
		addToOpenList(s.getStartState());
		HashSet<State <T>> closedSet=new HashSet<State<T>>();

		while(openList.size()>0)
		{
		    State<T> current=popOpenList();// dequeue
		    closedSet.add(current);

		    if(current.equals(s.getGoalState()))
		    {
		    	return backTrace(current, s.getStartState()); // private method, back traces through the parents  
		    }
		      
		    ArrayList<State<T>> successors=s.getAllPossibleStates(current); 
		    for(State<T> state : successors)
		    {
		    	//check if the state is not in closedSet and openList
		    	if(!closedSet.contains(state) && !openListContains(state)) 
		    	{
		    		state.setCameFrom(current); //set the state camefrom to current
		    		temp=state.UpdateCost() + heuristic.h(current, s.getGoalState()); //calculate the g() (path until this point like in BFS) + h() (the path left from calc by heuristic)
		    		state.setCost(temp); //set the cost of the state
		    		addToOpenList(state);
		    	}
		    	//check if the state is in openList and the cost of the current state is less then the cost of the state 
		    	else if(openListContains(state)) 
		    	{
		    		State <T> tempState;
		    		Iterator<State <T>> it = openList.iterator();
		    		while (it.hasNext())
		    		{
		    			tempState=it.next();
		    			if (tempState.equals(state) && current.getCost()<tempState.getCameFrom().getCost())
			    		{
		    				openList.remove(state);
			    			state.setCameFrom(current); //set the state camefrom to current
			    			temp=state.UpdateCost() + heuristic.h(current, s.getGoalState()); //calculate the g() (path until this point like in BFS) + h() (the path left from calc by heuristic)
				    		state.setCost(temp); //set the cost of the state
			    			addToOpenList(state);
			    			break;
			    		}
		    		}	
		    	}
		    	//check if the state is in closedSet and the cost of the current state is less then the cost of the state 
	    		else if((closedSet.contains(state)))
		    	{
	    			State <T> tempState;
	    			Iterator<State <T>> it = closedSet.iterator();
		    		while (it.hasNext())
		    		{
		    			tempState=it.next();
		    			if (tempState.equals(state) && current.getCost()<tempState.getCameFrom().getCost())
			    		{
			    			state.setCameFrom(current); //set the state camefrom to current
			    			temp=state.UpdateCost() + heuristic.h(current, s.getGoalState()); //calculate the g() (path until this point like in BFS) + h() (the path left from calc by heuristic)
				    		state.setCost(temp); //set the cost of the state
			    			addToOpenList(state);
		    				closedSet.remove(state);
		    				break;
			    		}			
		    		}
		    	}
		    }
		}
		return null; //if the loop ended there is no solution return NULL
	}

}
