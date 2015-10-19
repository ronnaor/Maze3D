package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenarators.Maze3d;
import algorithms.mazeGenarators.Position;


/**
* this is (1) the common type, and (2) a type of widget
* (1) we can switch among different MazeDisplayers
* (2) other programmers can use it naturally
*/
public abstract class MazeDisplayer extends Canvas{
	
	Maze3d maze;

	/**
	 * Constructs a new instance of this class given its parent and a style value describing its behavior and appearance.
	 * @param parent contains relevant controls
	 * @param style define the style
	 */
	public MazeDisplayer(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * get the maze
	 * @return Maze3d
	 */
	public Maze3d getMaze() {
		return maze;
	}

	/**
	 * set maze
	 * @param maze  Maze3d
	 */
	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}

	/**
	 * set the character in  a position
	 * @param x x axis position
	 * @param y y axis position
	 * @param z z axis position
	 */
	public abstract  void setCharacterPosition(int x,int y, int z);

	/**
	 * move character one step up
	 */
	public abstract void moveUp();
	/**
	 * move character one step down
	 */
	public abstract  void moveDown();
	/**
	 * move character one step left
	 */
	public abstract  void moveLeft();
	/**
	 * move character one step Right
	 */
	public  abstract void moveRight();
	/**
	 * move character one step floor up
	 */
	public  abstract void movePageUp();
	/**
	 * move character one step floor down
	 */
	public  abstract void movePageDown();
	/**
	 * move character to start position of the maze
	 */
	public abstract void moveStart();
	/**
	 * move character to new position
	 * @param p position to move to
	 */
	public abstract void move(Position p);

}