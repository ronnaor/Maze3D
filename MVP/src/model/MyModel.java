package model;

import java.util.Observable;
/**
 * class MyModel that extends Observable and implements Model will be defining the data to be displayed
 *
 */
public class MyModel extends Observable implements Model {

	/**
	 * Override for Observable notifyObservers method
	 */
	@Override
	public void notifyObservers(Object obj) {
		// TODO Auto-generated method stub
		super.notifyObservers(obj);
	}
}
