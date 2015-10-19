package boot;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import presenter.Properties;

public class PropertiesRun {

	public static void main(String[] args) {
		
		XMLEncoder xml;
		XMLEncoder xmlGUI;
		XMLEncoder xmlCLI;
		
		try {
			/*xml = new XMLEncoder(new FileOutputStream("prop.xml"));
			xml.writeObject(new Properties(13, "my", "bfs", "CLI"));
			xml.close();*/
			xml = new XMLEncoder(new FileOutputStream("prop.xml"));
			xml.writeObject(new Properties(13, "my", "A* air", "GUI"));
			xml.close();
			xmlGUI = new XMLEncoder(new FileOutputStream("propGUI.xml"));
			xmlGUI.writeObject(new Properties(13, "my", "bfs", "GUI"));
			xmlGUI.close();
			xmlCLI = new XMLEncoder(new FileOutputStream("propCLI.xml"));
			xmlCLI.writeObject(new Properties(13, "my", "A* air", "CLI"));
			xmlCLI.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
	}
}
