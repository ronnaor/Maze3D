package db;

import org.hibernate.Session;

public class SaveToDB {	
	private Session session;
	
	public SaveToDB(Session session) {
		  if(session == null)
		    throw new 
		      RuntimeException("Invalid session object.");
		  this.session = session;
		 }
		 public void saveObj(DBObject obj){
		  session.save(obj);
		 }
		 public void updateObj(DBObject obj){
		  session.update(obj);
		 }
		 public void deleteObj(DBObject obj) {
		  session.delete(obj);
		 }
}

