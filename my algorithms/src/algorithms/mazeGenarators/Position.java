package algorithms.mazeGenarators;

import java.util.Random;

/**
 * Position class
 *
 */
public class Position {
	protected static Random posrand = new Random();
	private int x;
	private int y;
	private int z;
	
	/**
	 * CTOR
	 * @param posX x value
	 * @param posY y value
	 * @param posZ z value
	 */
	public Position(int posX, int posY, int posZ) { //constructor
		x=posX;
		y=posY;
		z=posZ;
	}
	/**
	 * copy CTOR 
	 * @param p Position we copy
	 */
	public Position(Position p) { // copy constructor 
		x=p.x;
		y=p.y;
		z=p.z;
	}
	
	//getters and setters
	/**
	 * getter for x
	 * @return x value
	 */
	public int getX() {
		return x;
	}
	/**
	 * setter for x
	 * @param x x value
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * getter for y
	 * @return y value
	 */
	public int getY() {
		return y;
	}
	/**
	 * setter for y
	 * @param y y value
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * getter for z
	 * @return z value
	 */
	public int getZ() {
		return z;
	}
	/**
	 * setter for z
	 * @param z z value
	 */
	public void setZ(int z) {
		this.z = z;
	}
	
	/**
	 * Override object method toString
	 * change the default to string method of object
	 * @return the string we will get for Position
	 */
	@Override //change the default to string method of object
	public String toString() {
		return String.format("{ " + x + ", " + y + ", " + z + "}");
	}
	
	/**
	 * Override object method equals
	 * @param obj the object we will check if equal
	 * @return true if equal and false if not
	 */
	@Override
    public boolean equals(Object obj){ //  override to Object's equals method
		if ( (((Position)obj).getX()==this.x) && (((Position)obj).getY()==this.y) && (((Position)obj).getZ()==this.z) )
		{
			return true;
		}
        return false;
    }
	/**
	 * get position that can be start or goal of maze
	 * @param x x value
	 * @param y y value
	 * @param z z value
	 * @return position that can be start or goal of the maze
	 */
	public static Position startGoalPos(int x, int y, int z) //returns a position that can be start or goal of the maze
	{
		Position pos;
		int posX = posrand.nextInt(x);
		int posY = posrand.nextInt(y);
		int posZ = posrand.nextInt(z);
		
		if ((posX != 0) && (posX != x-1) && (posY != 0) && (posY != y-1) && (posZ != 0) && (posZ != z-1))
		{
			int selectAxis = posrand.nextInt(3); //select which axis will be ton the border x,y or z
			int selectSide = posrand.nextInt(2); // select if it will be on the start of the axis (0) or the end (axis-1)
			if (selectAxis == 0) 
			{
				if (selectSide == 0) 
				{
					posX = 0;
				}
				if (selectSide == 1) 
				{
					posX = x-1;
				}
			}	
			else if (selectAxis == 1)
			{
				if (selectSide == 0) 
				{
					posY = 0;
				}
				if (selectSide == 1) 
				{
					posY = y-1;
				}
			}
			else if (selectAxis == 2) 
			{
				if (selectSide == 0) 
				{
					posZ = 0;
				}
				if (selectSide == 1) 
				{
					posZ = z-1;
				}
			}
		}
		pos = new Position(posX,posY,posZ);
		return pos;	
	}

}
