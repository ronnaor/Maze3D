package db;

import java.io.Serializable;

import algorithms.mazeGenarators.Position;

@SuppressWarnings("serial")
public class Positions implements Serializable {

	public Positions(int posX, int posY, int posZ) {
		this.x = posX;
		this.y = posY;
		this.z = posZ;
	}
	
	public Positions(Position p) {
		this.x = p.getX();
		this.y = p.getY();
		this.z = p.getZ();
	}
	public int x;
	public int y; 
	public int z;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	
}
