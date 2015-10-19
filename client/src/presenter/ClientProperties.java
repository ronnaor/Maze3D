package presenter;

import java.io.Serializable;


/**
 * ClientProperties class of the properties of the client
 *
 */
public class ClientProperties  implements Serializable{

	/**
	 *  serialVersionUID is used as a version control in a Serializable class
	 */
	private static final long serialVersionUID = 1L;
	
	private String ipServer;
	private int portServer;
	
	/**
	 * default comstructor
	 */
	public ClientProperties() {
		this.ipServer=null;
		this.portServer=0;
	}
	/**
	 * constructor
	 * @param ipServer the ip of the server we use
	 * @param portServer the port of the server
	 */
	public ClientProperties(String ipServer, int portServer) {
		this.ipServer=ipServer;
		this.portServer=portServer;
	}
	
	/**
	 * copy constructor
	 * @param sp serverProperties we copy
	 */
	public ClientProperties(ClientProperties cp) {
		this.ipServer=cp.ipServer;
		this.portServer=cp.portServer;
	}
	/**
	 * get the server ip address
	 * @return String the ip of the server
	 */
	public String getIpServer() {
		return ipServer;
	}
	/**
	 * set the server ip address
	 * @param ipServer String the ip of the server
	 */
	public void setIpServer(String ipServer) {
		this.ipServer = ipServer;
	}
	/**
	 * get the server port
	 * @return int the port
	 */
	public int getPortServer() {
		return portServer;
	}
	/**
	 * set the server port
	 * @param portServer  int the port
	 */
	public void setPortServer(int portServer) {
		this.portServer = portServer;
	}
	
	
}
