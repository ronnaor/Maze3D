package algorithms.demo;

import algorithms.search.Searcher;

/**
 * Task<T> interface
 * @param <T>  the type of we will work with
 */
public interface Task<T> {
	/**
	 *
	 * @param searcher Searcher<T> that we work with
	 */
	void doTask(Searcher<T> searcher);

}
