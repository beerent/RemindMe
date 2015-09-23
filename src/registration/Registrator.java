package registration;

import database.DatabaseHandler;
import user.UserDAO;
import user.UserManager;

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
		UserManager user_manager = new UserManager(this.database_handler);
		return user_manager.createNewUser(username, email, password);
		
	}

}
