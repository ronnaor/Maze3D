package model;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	private boolean killServer = true;
	private int numOfClients;
	private ClinetHandler clinetHandler;
	private Thread mainServerThread;
	private int clientsHandled=0;
	/**
	 * Ctor that gets the properties from the XML files and set them to the server
	 * If properties does not exist then it will take default values
	 */
	public MyModel() {
		this.clinetHandler = new MyClientHandler();
		try {// get properties
			XMLDecoder xml=new XMLDecoder(new FileInputStream("./resources/propServer.xml"));
			ServerProperties properties=(ServerProperties)xml.readObject();
			this.port = properties.getNumClients();
			this.numOfClients = properties.getNumClients();
			xml.close();
		} catch (FileNotFoundException e1) {

			this.port = 1234;
			this.numOfClients = 10;
		}
	}
	
	@Override
	public void openServer() 
		{
		try {
			server=new ServerSocket(port);
			server.setSoTimeout(10*1000);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		executer=Executors.newFixedThreadPool(numOfClients);
		
		mainServerThread=new Thread(new Runnable() {			
			@Override
			public void run() {
				while(!killServer){
					try {
						final Socket someClient=server.accept();
						if(someClient!=null){
							executer.execute(new Runnable() {									
								@Override
								public void run() {
									try{										
										clientsHandled++;
										System.out.println("\thandling client "+clientsHandled);
										clinetHandler.handleClient(someClient.getInputStream(), someClient.getOutputStream());
										someClient.close();
										System.out.println("\tdone handling client "+clientsHandled);										
									}catch(IOException e){
										e.printStackTrace();
									}									
								}
							});								
						}
					}
					catch (SocketTimeoutException e){
						System.out.println("no clinet connected...");
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println("done accepting new clients.");
			} // end of the mainServerThread task
		});
		
		mainServerThread.start();
		
	}
		
	

	@Override
	public void closeServer() {
		killServer = false;
		// do not execute jobs in queue, continue to execute running threads
				System.out.println("shutting down");
				executer.shutdown();
				// wait 10 seconds over and over again until all running jobs have finished
				@SuppressWarnings("unused")
				boolean allTasksCompleted=false;
				try {
					while(!(allTasksCompleted=executer.awaitTermination(10, TimeUnit.SECONDS)));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("all the tasks have finished");

				try {
					mainServerThread.join();		
					System.out.println("main server thread is done");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					server.close();
					System.out.println("server is safely closed");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

}
