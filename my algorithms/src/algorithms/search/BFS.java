package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * BFS generic class extends CommonSearcher<T>
 * @param <T> the type of class we will work with
 */
public class BFS<T> extends CommonSearcher<T> {
	
	/**
	 * Override Searcher method search (implement abstract method from CommonSearcher)
	 * @param s Searchable<T> type 
	 * @return Solution to the problem 
	 */
	@Override
	public Solution<T> search(Searchable<T> s)
	{
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
		    		state.UpdateCost(); //update the cost of the state
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
			    			state.UpdateCost(); //update the cost of the state
			    			addToOpenList(state);
			    			break;
			    		}
		    		}	
		    	}
		    	//check if the state is in closedSet and the cost of the current state is less then the cost of the state 
	    		else if(closedSet.contains(state))
		    	{
	    			State <T> tempState;
	    			Iterator<State <T>> it = closedSet.iterator();
		    		while (it.hasNext())
		    		{
		    			tempState=it.next();
		    			if (tempState.equals(state) && current.getCost()<tempState.getCameFrom().getCost())
			    		{
			    			state.setCameFrom(current); //set the state camefrom to current
			    			state.UpdateCost(); //update the cost of the state
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
