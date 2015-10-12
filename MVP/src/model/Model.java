package model;

import java.util.HashMap;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;
import algorithms.search.Solution;
import db.DBObject;

/**
 * interface Model
 *
 */
public interface Model {

	/**
	 * get lost of strings with the names of the files in a path
	 * @param args array of strings that contains the path
	 */
	void getDir(String[] args);
	
	 /**
	 * saving the data to the DB
	 * @param obj the object we will save to the db
	 */
	void save2DB(DBObject obj);
	/**
	 * merging the mazes and solutions hasmpas into one hashmap
	 * @return one hashmap all the mazes, solutions and names of this maze 
	 */
	HashMap<String, HashMap<Maze3d, Solution<Position>>> getMazesNSolutions();

	/**
	 * Generating maze in new thread
	 * @param args array of strings, the name of the maze, and the x,y,z sizes
	 */
	void generate3DMaze(String[] args);

	/**
	 * getting the maze
	 * @param args the maze we want
	 * @return the maze
	 */
	Maze3d getMaze(String[] args);

	/**
	 * getting a 2D array that represent the maze by section chosen
	 * @param args strings :section, index, maze name
	 * @return 2D array of ints
	 */
	int[][] getCrossSectionBy(String[] args);

	/**
	 * save maze to a file
	 * @param args array of strings: the name of the maze we save, and the name of the file we save to
	 */
	void saveMaze(String[] args);

	/**
	 * load maze from a file 
	 * @param args array of strings: the name of the file we load from, and the name of the maze 
	 */
	void loadMaze(String[] args);

	/**
	 * getting the maze size from memory
	 * @param args stating which maze we want
	 * @return int size of the maze
	 */
	int getMazeSize(String[] args);

	/**
	 * getting file size 
	 * @param args stating which file we want
	 * @return long size of the file
	 */
	long getFileSize(String[] args);

	/**
	 * solve the maze
	 * @param args array of strings: the name of the maze, and the name of the algorithm we solve with 
	 * @return 
	 */
	void solve(String[] args);

	/**
	 * getting solution of the selected algorithm that solved the maze
	 * @param args the maze of which we want our solution
	 * @return the solution 
	 */
	Solution<Position> getSoultion(String[] args);

	/**
	 * exit from the run, close all open files and threads
	 * @param args  
	 */
	void exit(String[] args);
	
	/**
	 *method that creates db and it's tables only if they don't exist
	 *if they do exist, it loads the data from this tables 
	 */
	void startDB();

	/**
	 * set the properties of the model for the properties in the selected file
	 * @param args the path to the file
	 */
	void changeProperties(String[] args);

}
