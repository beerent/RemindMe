package user;

import java.util.Arrays;

import database.DatabaseHandler;

public class UserManager {
	private UserDAO userdao;
	private static final int min_username_length = 3;
	private static final int max_username_length = 20;

	public UserManager(DatabaseHandler database_handler){
		this.userdao = new UserDAO(database_handler);
	}
	
	public boolean createNewUser(String username, String email, String password){
		if(usernameExists(username) || usernameChecker(username))
			return false;
		
		if(emailExists(email) || emailChecker(email))
			return false;
		
		if(!passwordChecker(password))
			return false;
		
		this.userdao.registerUser(username, email, password);
		return true;
	}

	
	
	
	public boolean updateEmail(User user, String new_email){
		if(emailExists(new_email))
			return false;
		
		if(!emailChecker(new_email))
			return false;
		this.userdao.updateUserEmail(user, new_email);
		return true;
	}
	
	private boolean emailExists(String new_email){
		return this.userdao.emailExists(new_email);
	}
	
	private boolean emailChecker(String new_email){
		if(!new_email.contains(".") || !new_email.contains("@"))
				return false;
		if(new_email.length() < 5)
			return false;
		return true;
	}
	
	
	
	
	public boolean updateUsername(User user, String new_username){
		if (usernameExists(new_username))
			return false;
		
		if(!usernameChecker(new_username))
			return false;
		
		this.userdao.updateUserUsername(user, new_username);
		return true;
	}

	private boolean usernameExists(String username){
		return this.userdao.usernameExists(username);
	}
		
	private boolean usernameChecker(String username){
		int len = username.length();
		return len <= max_username_length && len >= min_username_length;
	}
	
	
	
	
	public boolean updatePassword(User user, String new_password){
		if(!passwordChecker(new_password))
			return false;
		
		this.userdao.updateUserPassword(user, new_password);
		return true;
	}
	
	private boolean passwordChecker(String password){
		if(password.length() < 7 || password.length() > 20)
			return false;

		String [] specialChars = {"!", "@", "#", "$", "%", "&", "*", "(", ")", "-", "_", "+", "=", "~", "`"};

		boolean containsSpecial = false;
		boolean containsCapital = false;
		boolean containsLower   = false;
		boolean containsNumber  = false;
		
		for(int i = 0; i < password.length(); i++){
			if(containsSpecial && containsCapital && containsLower && containsNumber)
				return true;
			
			char curr_char = password.charAt(i);
			
			if(!containsSpecial && Arrays.asList(specialChars).contains(curr_char)){
				containsSpecial = true;
				continue;
			}
			
			if(!containsCapital && curr_char > 64 && curr_char < 91){
				containsCapital = true;
				continue;
			}
			
			if(!containsLower && curr_char > 96 && curr_char < 123){
				containsLower = true;
				continue;
			}
			
			if(!containsNumber && curr_char > 47 && curr_char < 58){
				containsNumber = true;
				continue;
			}
				
		}
		
		return containsSpecial && containsCapital && containsLower && containsNumber;
	}
}
