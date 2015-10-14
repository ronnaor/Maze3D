package view;

import java.util.HashMap;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;


public abstract class BasicWindow implements Runnable{
	
	Display display;
	Shell shell;
	HashMap<String, Listener> listeners;
	Boolean displayDisposed;
	
 	public BasicWindow(String title, int width,int height,HashMap<String, Listener> inListeners) {
 		if(Display.getCurrent()==null)
 		{
 			this.display=new Display();
 			displayDisposed=true;
 		}
 		else
 		{
 			this.display=Display.getCurrent();
 			displayDisposed=false;
 		}
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

		 // dispose OS components
		 if(displayDisposed)
		 {
			 display.dispose();
		 }
	}

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public HashMap<String, Listener> getListeners() {
		return listeners;
	}

	public void setListeners(HashMap<String, Listener> listeners) {
		this.listeners = listeners;
	}

	public Boolean getDisplayDisposed() {
		return displayDisposed;
	}

	public void setDisplayDisposed(Boolean displayDisposed) {
		this.displayDisposed = displayDisposed;
	}
	
	
}
