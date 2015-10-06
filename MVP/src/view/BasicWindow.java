package view;

import java.util.HashMap;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;


public abstract class BasicWindow implements Runnable{
	
	Display display;
	Shell shell;
	HashMap<String, Listener> listeners;
	
 	public BasicWindow(String title, int width,int height,HashMap<String, Listener> inListeners) {
 		this.display=new Display();
 		this.shell  = new Shell(display);
 		this.shell.setSize(width,height);
 		this.shell.setText(title);
 		this.listeners=inListeners;
	}
 	
 	abstract void initWidgets();
 	/**
 	 * closing the window
 	 */
 	abstract void close();


	@Override
	public void run() {
		initWidgets();
		shell.open();
		// main event loop
		 while(!shell.isDisposed()){ // while window isn't closed

		    // 1. read events, put then in a queue.
		    // 2. dispatch the assigned listener
		    if(!display.readAndDispatch()){ 	// if the queue is empty
		       display.sleep(); 			// sleep until an event occurs 
		    }

		 } // shell is disposed

		 display.dispose(); // dispose OS components
	}
	

}
