package user;

public class User {
	private String username;
	private int user_id;
	
	public User(String username, int user_id){
		this.username = username;
		this.user_id = user_id;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public int getUserID(){
		return this.user_id;
	}
}
