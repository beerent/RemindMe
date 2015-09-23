package user;

import database.DatabaseHandler;

public class UserAuthenticator {
	private DatabaseHandler database_handler;
	
	public UserAuthenticator(DatabaseHandler database_handler){
		this.database_handler = database_handler;
	}
	
	public User authenticateUser(int user_id, String password){
		UserDAO userDao = new UserDAO(this.database_handler);
		
		User user = userDao.getUserByID(user_id);
		if (user == null)
			return null;
		return authenticateUser(user, password);
	}
	
	
	public User authenticateUser(String user_name, String password){
		UserDAO userDao = new UserDAO(database_handler);
		
		User user = userDao.getUserByUsername(user_name);
		if (user == null)
			return null;
		return authenticateUser(user, password);
	}
	
	private User authenticateUser(User user, String password){
		UserDAO userDao = new UserDAO(database_handler);
		 boolean authenticated = userDao.userAndPasswordMatch(user, password);
		 if(authenticated)
			 return user;
		 return null;
	}
}
