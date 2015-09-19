package controller;
/**
 * 
 *command template interface
 * @param <T> the object type of the command
 */
public interface Command <T>{
	/**
	 * doing the command
	 * @param args the template array objects
	 */
	void doCommand(T[] args);
}
