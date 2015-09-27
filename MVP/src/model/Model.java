package model;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;
import algorithms.search.Solution;

/**
 * interface Model
 *
 */
public interface Model {

	void getDir(String[] args);

	void generate3DMaze(String[] args);

	Maze3d getMaze(String[] args);

	int[][] getCrossSectionBy(String[] args);

	void saveMaze(String[] args);

	void loadMaze(String[] args);

	int getMazeSize(String[] args);

	long getFileSize(String[] args);

	void solve(String[] args);

	Solution<Position> getSoultion(String[] args);

	void exit(String[] args);

}
