package model;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClinetHandler {
	void handleClient(InputStream inFromClient, OutputStream outToClient);
}
