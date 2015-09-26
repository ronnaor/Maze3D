package view;

import java.util.Observable;

/**
 * 
 * class MyView extends Observable implements View, 
 * displays data and user commands to the presenter to act upon that data.
 */
public class MyView extends Observable implements View {

	
	/**
	 * Override for Observable notifyObservers method
	 */
	@Override
	public void notifyObservers(Object obj) {
		// TODO Auto-generated method stub
		super.notifyObservers(obj);
	}
}
