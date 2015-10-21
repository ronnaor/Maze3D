package model;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * interface ClinetHandler with the methods to handle a client in the server side
 *
 */
public interface ClinetHandler {
	/**
	 * handle connection with a client
	 * @param inFromClient InputStream from client
	 * @param outToClient OutputStream from client
	 */
	void handleClient(InputStream inFromClient, OutputStream outToClient);
}
