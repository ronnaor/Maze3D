package view;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;

public class StartWindow extends MazeWindow {

	private String[] commands;
	
	public StartWindow(String title, int width, int height, HashMap<String, Listener> listeners) {
		super(title, width, height, listeners);
		//setting the print for the menu
		commands =new String[] {"exit","dir","generate 3d maze","display","display cross section by","save maze","load maze","maze size",
		"file size","solve","display solution"};
	}

	@Override
	void initWidgets() 
	{
	FillLayout fillLayout = new FillLayout();
	fillLayout.type = SWT.VERTICAL;
	shell.setLayout(fillLayout);
	
	Group commandGroup = new Group(shell, SWT.NULL);
	commandGroup.setText("Choose Command:");
	GridLayout gridLayout = new GridLayout();
    commandGroup.setLayout(gridLayout);
    GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
    gridData.horizontalSpan = 2;
    commandGroup.setLayoutData(gridData);
	Combo combo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
	combo.setItems(commands);
	combo.addTraverseListener(new TraverseListener() {
	      public void keyTraversed(TraverseEvent e) {
	    	  int style = SWT.ICON_INFORMATION;
	    	  MessageBox messageBox = new MessageBox(shell, style);
	    	    messageBox.setMessage("Message");
	      }
	    });
    Group info = new Group(shell, SWT.NULL);
     info.setText("Info");
     GridLayout gridLayout2 = new GridLayout();
     gridLayout2.numColumns = 2;
     info.setLayout(gridLayout);
     GridData gridData2 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
     gridData2.horizontalSpan = 2;
     info.setLayoutData(gridData);
    
     new Label(info, SWT.NULL).setText("Insert first argument:");
     Text first = new Text(info, SWT.SINGLE | SWT.BORDER);
     first.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    
     new Label(info, SWT.NULL).setText("Insert seconed argument:");
     Text seconed = new Text(info, SWT.SINGLE | SWT.BORDER);
     seconed.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
     
     new Button(shell, SWT.PUSH).setText("ok");
}


public void showMessageBox(Exception e)
{
	MessageBox messageBox = new MessageBox(shell,  SWT.ICON_ERROR| SWT.OK);
	messageBox.setMessage(e.getMessage());
	messageBox.setText("Error");
	messageBox.open();
}
}
