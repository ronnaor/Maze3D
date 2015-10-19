package presenter;

import java.io.Serializable;

/**
 * serverProperties class of the properties of the server
 *
 */
public class serverProperties implements Serializable {

	/**
	 *  serialVersionUID is used as a version control in a Serializable class
	 */
	private static final long serialVersionUID = 1L;
	
	private int port;
	private int numClients;
	
	/**
	 * default comstructor
	 */
	public serverProperties() {
		this.port=0;
		this.numClients=0;
	}
	/**
	 * constructor
	 * @param port port we use
	 * @param numClients number of clients
	 */
	public serverProperties(int port, int numClients) {
		this.port=port;
		this.numClients=numClients;
	}
	
	/**
	 * copy constructor
	 * @param sp serverProperties we copy
	 */
	public serverProperties(serverProperties sp) {
		this.port=sp.port;
		this.numClients=sp.numClients;
	}
	
	/**
	 * get port
	 * @return int the port
	 */
	public int getPort() {
		return port;
	}
	/**
	 * set the port
	 * @param port int the port
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * get the number of clients
	 * @return int the number of clients
	 */
	public int getNumClients() {
		return numClients;
	}
	/**
	 * set the number of clients
	 * @param numClients int the number of clients
	 */
	public void setNumClients(int numClients) {
		this.numClients = numClients;
	}
	
	
	
}
