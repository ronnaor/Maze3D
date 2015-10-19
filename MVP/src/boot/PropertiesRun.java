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
			/*xml = new XMLEncoder(new FileOutputStream("./resources/prop.xml"));
			xml.writeObject(new Properties(13, "my", "A* manhatten", "CLI"));
			xml.close();*/
			xml = new XMLEncoder(new FileOutputStream("./resources/prop.xml"));
			xml.writeObject(new Properties(13, "my", "A* air", "GUI"));
			xml.close();
			xmlGUI = new XMLEncoder(new FileOutputStream("./resources/propGUI.xml"));
			xmlGUI.writeObject(new Properties(13, "my", "A* manhatten", "GUI"));
			xmlGUI.close();
			xmlCLI = new XMLEncoder(new FileOutputStream("./resources/propCLI.xml"));
			xmlCLI.writeObject(new Properties(13, "my", "bfs", "CLI"));
			xmlCLI.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
	}
}
