package user;

import database.DatabaseHandler;
import database.QueryResult;

public class UserDAO {
	private DatabaseHandler database_handler;
	
	public UserDAO(DatabaseHandler database_handler){
		this.database_handler = database_handler;
	}
	
	public User getUserByID(int user_id){
		String sql = "select user_name from users where user_id = '"+ user_id +"'";
		QueryResult result = database_handler.executeQuery(sql);
		if (result.containsData())
			return new User(result.getElement(0, 0), user_id);
		return null;
	}
	
	public User getUserByUsername(String user_name){
		String sql = "select user_id from users where username = '"+ user_name +"'";
		QueryResult result = database_handler.executeQuery(sql);
		if (result.containsData())
			return new User(user_name, Integer.parseInt(result.getElement(0, 0)));
		return null;
	}
	
	public String getUserPassword(User user){
		String sql = "select password from users where user_id = " + user.getUserID();
		QueryResult result = database_handler.executeQuery(sql);
		if (result.containsData())
			return result.getElement(0, 0);
		return null;
	}
	
	public boolean userAndPasswordMatch(User user, String password){
		String sql = "select * from users where user_id = " + user.getUserID() + " and password = '"+ password +"'";
		QueryResult result = database_handler.executeQuery(sql);
		return result.containsData();
			
	}
	
	public boolean usernameExists(String username){
		String sql = "select * from users where username = '"+ username +"'";
		QueryResult result = database_handler.executeQuery(sql);
		return result.containsData();
	}
	
	public boolean emailExists(String email){
		String sql = "select * from users where email = '"+ email +"'";
		QueryResult result = database_handler.executeQuery(sql);
		return result.containsData();
	}
	

	public void updateUserPassword(User user, String new_password) {
		String sql = "update users set password = '"+ new_password +"' where user_id = " + user.getUserID();
		database_handler.executeInsert(sql);
	}

	public void updateUserEmail(User user, String new_email) {
		String sql = "update users set email = '"+ new_email +"' where user_id = " + user.getUserID();
		database_handler.executeInsert(sql);
	}
	
	public void updateUserUsername(User user, String username){
		String sql = "update users set username = '"+ username +"' where user_id = " + user.getUserID();
		database_handler.executeInsert(sql);
	}

	public void registerUser(String username, String email, String password) {
		String sql = "insert into users (username, email, password) values ('"+ username +"', '"+ email +"', '"+ password +"')";
		database_handler.executeInsert(sql);
	}
}