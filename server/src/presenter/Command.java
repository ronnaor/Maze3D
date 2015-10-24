package presenter;
/**
 * 
 *interface that detriments where are the commands
 * @param <T> the return type of the command
 */
public interface Command<T> {

	/**
	 * do the command
	 * @param args the name of the command
	 * @return the type of the given command
	 */
	T doCommand(String[] args);
}
