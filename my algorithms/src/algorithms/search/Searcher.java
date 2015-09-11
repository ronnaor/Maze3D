package algorithms.search;

/**
 * generic interface Searcher
 * @param <T> the type of we will work with 
 */
public interface Searcher<T> {
	/**
	 * the search method
	 * @param s Searchable<T> type 
	 * @return Solution to the problem 
	 */
    public Solution<T> search(Searchable<T> s);
 
    /**
     * get how many nodes were evaluated by the algorithm
     * @return the number of nodes evaluated
     */
    public int getNumberOfNodesEvaluated();
}

