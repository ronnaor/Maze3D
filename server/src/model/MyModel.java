package model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import presenter.ServerProperties;

public class MyModel extends Observable implements Model {

	private int port;
	private ExecutorService executer;
	private ServerSocket server;
	private boolean killServer;
	private boolean serverOpen;
	private int numOfClients;
	private ClinetHandler clinetHandler;
	private Thread mainServerThread;
	private int clientsHandled;
	/**
	 * Ctor that gets the properties from the XML files and set them to the server
	 * If properties does not exist then it will take default values
	 */
	public MyModel() {
		this.clinetHandler = new MyClientHandler();
		this.clientsHandled = 0;
		this.killServer = false;
		this.serverOpen = false;
		File serverProp=new File("./resources/propServer.xml"); // check if server Properties xml file exists
		if(!serverProp.exists()) //if the file does not exists
		{
			try { //create the xml file
				XMLEncoder xml = new XMLEncoder(new FileOutputStream("./resources/propServer.xml"));
				xml.writeObject(new ServerProperties(1234, 10));
				xml.close();
			} catch (FileNotFoundException e) {
				setChanged();
				notifyObservers(new String[] {"error", e.getMessage()});
			}
			
		}
		
		try {// get the server properties from the xml file
			XMLDecoder xml=new XMLDecoder(new FileInputStream("./resources/propServer.xml"));
			ServerProperties properties=(ServerProperties)xml.readObject();
			this.port = properties.getPort();
			this.numOfClients = properties.getNumClients();
			xml.close();
		} catch (Exception e) {
			setChanged();
			notifyObservers(new String[] {"error", e.getMessage()});
		}
	}
	
	@Override
	public void openServer() 
		{
		if (!serverOpen)
		{
			try {
				server=new ServerSocket(port);
				server.setSoTimeout(10*1000);
			} catch (SocketException e1) {
				setChanged();
				notifyObservers(new String[] {"error", e1.getMessage()});
			} catch (IOException e1) {
				setChanged();
				notifyObservers(new String[] {"error", e1.getMessage()});
			}

			executer=Executors.newFixedThreadPool(numOfClients);
			this.killServer = false;
			mainServerThread=new Thread(new Runnable() {			
				@Override
				public void run() {
					while(!killServer){
						try {
							final Socket someClient=server.accept();
							
							setChanged();
							notifyObservers(new String[] {"update", someClient.toString()});
							
							if(someClient!=null){
								executer.execute(new Runnable() {									
									@Override
									public void run() {
										try{										
											clientsHandled++;
											setChanged();
											notifyObservers(new String[] {"update", "\thandling client nomber: "+clientsHandled});
											clinetHandler.handleClient(someClient.getInputStream(), someClient.getOutputStream());
											someClient.close();
											setChanged();
											notifyObservers(new String[] {"update", "\tdone handling client "+clientsHandled});									
										}catch(IOException e){
											setChanged();
											notifyObservers(new String[] {"error", e.getMessage()});
										}									
									}
								});								
							}
						}
						catch (SocketTimeoutException e){
							setChanged();
							notifyObservers(new String[] {"error", "no clinet connected"});
						} 
						catch (IOException e) {
							setChanged();
							notifyObservers(new String[] {"error", e.getMessage()});
						}
					}
					setChanged();
					notifyObservers(new String[] {"update", "done accepting new clients"});
				} // end of the mainServerThread task
			});
			
			mainServerThread.start();
			
			setChanged();
			notifyObservers(new String[] {"update", "server is now open, can accept clients"});
			this.serverOpen = true;
			
		}
		else
		{
			setChanged();
			notifyObservers(new String[] {"update", "server already open"});
		}
	}
		
	

	@Override
	public void closeServer() {
		if (serverOpen)
		{
			killServer = true;
			serverOpen = false;
			// do not execute jobs in queue, continue to execute running threads
					System.out.println("shutting down the server");
					executer.shutdown();
					// wait 10 seconds over and over again until all running jobs have finished
					@SuppressWarnings("unused")
					boolean allTasksCompleted=false;
					try {
						while(!(allTasksCompleted=executer.awaitTermination(10, TimeUnit.SECONDS)));
					} catch (InterruptedException e) {
						setChanged();
						notifyObservers(new String[] {"error", e.getMessage()});
					}
					
					setChanged();
					notifyObservers(new String[] {"update", "all tasks finished working"});

					try {
						mainServerThread.join();
						setChanged();
						notifyObservers(new String[] {"update", "main server thread is done"});
					} catch (InterruptedException e) {
						setChanged();
						notifyObservers(new String[] {"error", e.getMessage()});
					}
					
					try {
						server.close();
						setChanged();
						notifyObservers(new String[] {"update", "server closed goodbye"});
					} catch (IOException e) {
						setChanged();
						notifyObservers(new String[] {"error", e.getMessage()});
					}
		}
		else
		{
			setChanged();
			notifyObservers(new String[] {"update", "server already closed goodbye"});
		}
		
	}

}
