package client;

import database.DatabaseHandler;
import user.User;

public class GUIRunner {
	private GUIManager guimanager;
	private Login login;
	private Register register;
	private Main main;
	
	private User user;
	private String password;
	
	private DatabaseHandler database_handler;
	
	private boolean server_is_running;
	
	public static void main(String [] args){
		GUIRunner guirunner = new GUIRunner();
		guirunner.loadLogin();
	}
	
	public GUIRunner(){
		this.login = null;
		this.register = null;
		this.main = null;
		this.database_handler = null;
		this.user = null;
		this.guimanager = new GUIManager();
		this.server_is_running = false;
	}
	
	public boolean serverIsRunning(){
		return this.server_is_running;
	}
	
	public DatabaseHandler getDatabaseHandler(){
		if(this.database_handler == null)
			this.database_handler = new DatabaseHandler();
		return this.database_handler;
	}
	
	public void setUser(User user){
		this.user = user;
	}
	
	public User getUser(){
		return this.user;
	}
	
	public void loadLogin(){
		this.guimanager.clear();
		if(this.login == null)
			this.login = new Login(this.guimanager, this);
		this.login.load();
		this.guimanager.appear();
	}
	
	public void loadRegister(){
		this.guimanager.clear();
		if(this.register == null)
			this.register = new Register(this.guimanager, this);
		this.register.load();
	}
	
	public void loadMain(){
		this.guimanager.clear();
		if(this.main == null)
			this.main = new Main(this.guimanager, this);
		this.main.load();
	}

	public void startServer() {
		this.server_is_running = true;
	}
	
	public void stopServer(){
		this.server_is_running = false;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword(){
		return this.password;
	}
}
