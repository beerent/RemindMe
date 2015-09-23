package server;

import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import database.DatabaseHandler;
import logger.Logger;
import registration.Registrator;
import reminder.Reminder;
import reminder.ReminderDAO;
import socketio.SocketReader;
import socketio.SocketWriter;
import user.User;
import user.UserAuthenticator;
import xml.XMLWriter;

public class Listener extends Thread{
	private DatabaseHandler database_handler;
	private Logger log;
	private Socket socket;
	private static boolean run;
	
	private boolean quit;

	public Listener(Socket socket) {
		this.database_handler = new DatabaseHandler();
		Listener.run = false;
		this.socket = socket;
		this.log = Logger.getInstance();
		this.quit = false;
	}
	

	public static void setRun(boolean run){
		Listener.run = run;
	}
	
	public void run(){
		SocketReader socket_reader = new SocketReader(socket);
		SocketWriter socket_writer = new SocketWriter(socket);
		
		String operation = socket_reader.read();
		
		serverLog(1, "opertation requested: " + operation);
		switch(operation){
			case "auth":
				authenticate(socket_writer, socket_reader);
				break;
			case "login":
				login(socket_writer, socket_reader);
				break;
			case "register":
				register(socket_writer, socket_reader);
				break;
		}
	}
	
	private void authenticate(SocketWriter socket_writer, SocketReader socket_reader){
		socket_writer.write("OK"); //OK
		String username = socket_reader.read();
		String password = socket_reader.read();
		
		UserAuthenticator user_auth = new UserAuthenticator(this.database_handler);
		User user = user_auth .authenticateUser(username, password);
		
		if(user == null)
			socket_writer.write("denied");
		
		serverLog(2, "user: " + user.getUsername() + " (" + user.getUserID() + ") authenticated.");
		
		JSONObject user_json = new JSONObject();
		try {
			user_json.put("username", user.getUsername());
			user_json.put("user_id", user.getUserID());
		} catch (JSONException e2) {
			e2.printStackTrace();
		}
		
		socket_writer.write(user_json.toString());	
	}
	
	private void login(SocketWriter socket_writer, SocketReader socket_reader){
		UserAuthenticator user_auth = new UserAuthenticator(this.database_handler);
		
		socket_writer.write("OK"); //OK
		String username = socket_reader.read();
		String password = socket_reader.read();
		
		User user = user_auth.authenticateUser(username, password);
		
		if(user == null)
			invalidUser(username);
		
		if(this.quit)
			return;
		
		serverLog(2, "user: " + user.getUsername() + " (" + user.getUserID() + ") connected.");
		socket_writer.write("OK");
		serverLog(4, "responded OK to user");
		
		ArrayList<String> request = new ArrayList<String>();
		String option = socket_reader.read();
		while(!option.equals( "^]")){
			request.add(option);
			option = socket_reader.read();		
		}
		
		int i = 0;
		ReminderDAO reminder_dao = new ReminderDAO(this.database_handler);
		switch(request.get(i++)){
			case "personal":
				serverLog(4, "personal");
				switch(request.get(i++)){
					case "info":
						break;
				}
				break;
				
			case "get":
				serverLog(4, "get");
				switch(request.get(i++)){
					case "user":
						serverLog(4, "user");
						String requested_username = request.get(i);					
						break;
					case "updates":
						serverLog(4, "updates");
						socket_writer.write("1");
						break;
					case "reminders":
						ArrayList<Reminder> reminders;
						serverLog(4, "reminders");
						String reminder_op = null;
						switch(request.get(i++)){
							case "new":
								reminder_op = "new";
								break;
							case "read":
								reminder_op = "read";
								break;
							case "all":
								reminder_op = "all";
								break;
						}
						reminders = reminder_dao.getReminders(user, reminder_op);
						serverLog(2, "sending " + reminders.size() + " reminders");
						try{
							JSONObject reminder_json = new JSONObject();
							JSONArray reminder_json_array = new JSONArray();
							for(Reminder reminder : reminders){
								JSONObject temp_json = new JSONObject();
								temp_json.put("id", reminder.getReminderID());
								temp_json.put("text", reminder.getReminder());
								String read = "0";
								if(reminder.isRead())
									read = "1";
								temp_json.put("read", read);
								reminder_json_array.put(temp_json);
							}
							reminder_json.put("reminder", reminder_json_array);
							socket_writer.write(reminder_json.toString());
						}catch(Exception e){
							e.printStackTrace();
						}
						break;
					case "reminder_count":
						serverLog(4, "reminder_count");
						String count_op = request.get(i++);
						serverLog(4, "requested " + count_op + " reminders");
						int count = reminder_dao.getReminderCount(user, count_op);
						XMLWriter reminder_count_xml = new XMLWriter();
						reminder_count_xml.openParent("reminder_count");
						reminder_count_xml.addChild("count", ""+count);
						reminder_count_xml.closeParent("reminder_count");
					try {
						serverLog(2, "sending: " + reminder_count_xml.getXML());
						socket_writer.write(reminder_count_xml.getXML());
					} catch (Exception e) {
						e.printStackTrace();
					}
						break;
				}		
				break;
			case "add":
				serverLog(4, "add");
				switch(request.get(i++)){
					case "reminder":
						serverLog(4, "reminder");
						String reminder = request.get(i);
						serverLog(2, "adding reminder to database: " + reminder);
						reminder_dao.addReminder(user, reminder);
						socket_writer.write("OK");
						break;
				}
				break;
			case "update":
				serverLog(4, "update");
				switch(request.get(i++)){
					case "reminder":
						serverLog(4, "reminder");
						Reminder reminder = new Reminder(Integer.parseInt(request.get(i++)));
						serverLog(4, "updating reminder: " + reminder.getReminderID());
						switch(request.get(i++)){
							case "read":
								serverLog(4, "read");
								int read = Integer.parseInt(request.get(i));
								serverLog(4, "updating to read: " + read);
								reminder_dao.markReminderAsRead(user, reminder, read);
								break;
						}
						break;
				}
				break;
		}
	}
	
	private void register(SocketWriter socket_writer, SocketReader socket_reader){
		Registrator registrator = new Registrator(this.database_handler);
		socket_writer.write("OK");
		String username = socket_reader.read();
		String email = socket_reader.read();
		String password1 = socket_reader.read();
		String password2 = socket_reader.read();
		serverLog(2, "registering: + " + username + ", " + email + ", " + password1 + ", " + password2);
		registrator.registerUser(username, email, password1, password2);
		serverLog(2, "user: " + username + " logged");
	}
	
	private void invalidUser(String username){
		userLog(0, username, "user: " + username + " does not exist.");
		this.database_handler.disconnect();
		this.quit = true;
	}
	
	private void userLog(int level, String user, String log){
		this.log.log(level, "[ " + user + "] " + "LISTENER", log);
	}
	private void serverLog(int level, String log){
		this.log.log(level, "LISTENER", log);
	}
}
