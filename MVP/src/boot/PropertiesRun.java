package boot;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import presenter.Properties;

public class PropertiesRun {

	public static void main(String[] args) {
		
		XMLEncoder xml;
		
		try {
			xml = new XMLEncoder(new FileOutputStream("prop.xml"));
			xml.writeObject(new Properties(13, "my", "bfs", "CLI"));
			xml.close();
			/*xml = new XMLEncoder(new FileOutputStream("prop.xml"));
			xml.writeObject(new Properties(13, "my", "A* air", "GUI"));
			xml.close();*/
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
	}
}
