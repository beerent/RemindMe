package user;

import database.DatabaseHandler;
import database.QueryResult;

public class UserDAO {
	private static UserDAO instance = null;
	
	private UserDAO(){}
	
	public static UserDAO getInstance(){
		if(instance == null)
			instance = new UserDAO();
		return instance;
	}
	
	public User getUserByID(DatabaseHandler database_handler, int user_id){
		String sql = "select user_name from users where user_id = '"+ user_id +"'";
		QueryResult result = database_handler.executeQuery(sql);
		if (result.containsData())
			return new User(result.getElement(0, 0), user_id);
		return null;
	}
	
	public User getUserByUsername(DatabaseHandler database_handler, String user_name){
		String sql = "select user_id from users where username = '"+ user_name +"'";
		QueryResult result = database_handler.executeQuery(sql);
		if (result.containsData())
			return new User(user_name, Integer.parseInt(result.getElement(0, 0)));
		return null;
	}
	
	public String getUserPassword(DatabaseHandler database_handler, User user){
		String sql = "select password from users where user_id = " + user.getUserID();
		QueryResult result = database_handler.executeQuery(sql);
		if (result.containsData())
			return result.getElement(0, 0);
		return null;
	}
	
	public boolean userAndPasswordMatch(DatabaseHandler database_handler, User user, String password){
		String sql = "select * from users where user_id = " + user.getUserID() + " and password = '"+ password +"'";
		QueryResult result = database_handler.executeQuery(sql);
		return result.containsData();
			
	}
}