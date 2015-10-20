package presenter;

import java.io.Serializable;

/**
 * serverProperties class of the properties of the server
 *
 */
public class ServerProperties implements Serializable {

	/**
	 *  serialVersionUID is used as a version control in a Serializable class
	 */
	private static final long serialVersionUID = 1L;
	
	private int port;
	private int numClients;
	
	/**
	 * default constructor
	 */
	public ServerProperties() {
		this.port=0;
		this.numClients=0;
	}
	/**
	 * constructor
	 * @param port port we use
	 * @param numClients number of clients
	 */
	public ServerProperties(int port, int numClients) {
		this.port=port;
		this.numClients=numClients;
	}
	
	/**
	 * copy constructor
	 * @param sp serverProperties we copy
	 */
	public ServerProperties(ServerProperties sp) {
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
