package algorithms.demo;

import algorithms.mazeGenarators.Position;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import algorithms.search.Solution;

/**
 * MazeSearcherTask class implements Task<T> with type Position
 *
 */
public class MazeSearcherTask implements Task<Position> {

	
	private Searchable<Position> searchable; 
	/**
	 * CTOR
	 * @param searchable the searchable methods we will use 
	 */
	public MazeSearcherTask(Searchable<Position> searchable) {
		this.searchable = searchable;
	}
	
	/**
	 * Override Task method doTask
	 * @param searcher Searcher<Position> the kind of searcher we use   
	 */
	@Override
	public void doTask(Searcher<Position> searcher) {
		Solution<Position> s= searcher.search(searchable); //get Solution of the maze
		// print the amount of moves in the Solution path 
		System.out.println("the amount of moves in the Solution:");
		System.out.println(s.getNumMoves());
		// print the solution path
		System.out.println("the solution path is:");
		for (Position pos : s.getPath())
		{
			System.out.println(pos);
		}
		// print the amount of evaluated Nodes in the search
		System.out.println("the amount of evaluated Nodes in the search is:");
		System.out.println(searcher.getNumberOfNodesEvaluated());
		
		 
	}

}
