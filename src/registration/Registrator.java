package registration;

import database.DatabaseHandler;
import user.UserDAO;

public class Registrator {
	private DatabaseHandler database_handler;
	
	public Registrator(DatabaseHandler database_handler){
		this.database_handler = database_handler;
	}
	
	public boolean registerUser(String username, String email, String password1, String password2){
		if(!password1.equals(password2))
			return false;
		
		return registerUser(username, email, password1);
	}
	
	private boolean registerUser(String username, String email, String password){
		UserDAO userDao = UserDAO.getInstance();
		return userDao.registerUser(database_handler, username, email, password);
		
	}

}
