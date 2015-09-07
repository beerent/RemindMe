package user;

import java.security.NoSuchAlgorithmException;
import database.DatabaseHandler;

public class UserAuthenticator {
	private static UserAuthenticator user_auth = null;
	
	private UserAuthenticator(){}
	
	public static UserAuthenticator getInstance(){
		if(user_auth == null)
			user_auth = new UserAuthenticator();
		return user_auth;
	}
	
	public User authenticateUser(DatabaseHandler database_handler, int user_id, String password){
		UserDAO userDao = UserDAO.getInstance();
		
		User user = userDao.getUserByID(database_handler, user_id);
		if (user == null)
			return null;
		return authenticateUser(userDao, database_handler, user, password);
	}
	
	
	public User authenticateUser(String user_name, String password){
		UserDAO userDao = UserDAO.getInstance();
		DatabaseHandler database_handler = new DatabaseHandler();
		
		User user = userDao.getUserByUsername(database_handler, user_name);
		if (user == null)
			return null;
		return authenticateUser(userDao, database_handler, user, password);
	}
	
	private User authenticateUser(UserDAO userDao, DatabaseHandler database_handler, User user, String password){
		 boolean authenticated = userDao.userAndPasswordMatch(database_handler, user, password);
		 if(authenticated)
			 return user;
		 return null;
	}
}
