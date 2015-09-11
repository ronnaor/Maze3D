package algorithms.search;
/**
 * 
 *generic class State  that implements the interface  Comparable<Object>
 * @param <T> the type of class we will work with 
 */
public class State <T> implements Comparable<Object>{
	private T state;    // the state represented by a string
    private double cost;     // cost to reach this state
    private State<T> cameFrom;  // the state we came from to this state
    private double baseCost; //Remembers the cost before the last change

    /**
     * CTOR for State
     * @param state parameter type T
     * @param cost the cost for a move
     */
	public State(T state,double cost){    // CTOR    
        this.state = state;
        this.baseCost = cost;
        this.cost = cost;
    }
    /**
     * copy CTOR
     * @param state is a State<T> that we copy
     */
	public State(State<T> state)   //copy CTOR    
	{
		this.state = state.state;
		this.cost = state.cost;
		this.cameFrom = state.cameFrom;
		this.baseCost =state.baseCost;
	}
    /**
     * Override of Object's equals method
     * check if the object state is equal to this state
     * @param obj State<T> object  
     */
    @Override
    public boolean equals(Object obj){ //  override to Object's equals method
        return this.state.equals(((State<T>)obj).state);
    }
    /**
     * Override of Comparable compareTo method
     * compare two States
     * @param obj State<T> object 
     */
	@Override
	public int compareTo(Object obj) { //  override to Comparable compareTo method
		return (int) (this.cost-((State<T>)obj).getCost());
	}
	/**
	 * update the cost of the state
	 * @return the State cost
	 */
	public double UpdateCost() //update the cost of the state
	{
		cost = baseCost + cameFrom.getCost();
		return cost;
	}
	
	//getters and setters
	/**
	 * getter for cameFrom
	 * @return the State cameFrom
	 */
	public State<T> getCameFrom() {
		return cameFrom;
	}
	/**
	 * setter for cameFrom
	 * @param cameFrom  State<T> parameter to insert in cameFrom
	 */
	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom =  cameFrom;    
	}
	/**
	 * getter for cost
	 * @return the State cost
	 */
	public double getCost() {
		return cost;
	}
	/**
	 * setter for cost
	 * @param cost double parameter to insert in cost
	 */
	public void setCost(double cost) {
		this.cost = cost;
	} 
	/**
	 * getter for state
	 * @return the State cost state 
	 */
	public T getState() {
		return state;
	}
	/**
	 * setter for state
	 * @param state  T parameter to insert in state
	 */
	public void setState(T state) {
		this.state = state;
	}
	/**
	 * getter for baseCost
	 * @return the State baseCost
	 */
	public double getBaseCost() {
		return baseCost;
	}
	/**
	 * setter for cost
	 * @param baseCost double parameter to insert in baseCost
	 */
	public void setBaseCost(double baseCost) {
		this.baseCost = baseCost;
	}

}
