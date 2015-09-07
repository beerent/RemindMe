package server;

import java.net.Socket;
import java.util.ArrayList;

import database.DatabaseHandler;
import logger.Logger;
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
		UserAuthenticator user_auth = UserAuthenticator.getInstance();
		
		SocketReader socket_reader = new SocketReader(socket);
		SocketWriter socket_writer = new SocketWriter(socket);
		
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
		ReminderDAO reminder_dao = ReminderDAO.getInstance();
		switch(request.get(i++)){
			case "get":
				serverLog(4, "get");
				switch(request.get(i++)){
					case "updates":
						serverLog(4, "updates");
						socket_writer.write("1");
						break;
					case "reminders":
						ArrayList<Reminder> reminders;
						serverLog(4, "reminders");
						switch(request.get(i++)){
							case "new":
								reminders = reminder_dao.getReminders(database_handler, user);
								XMLWriter xml = new XMLWriter();
								serverLog(2, "sending " + reminders.size() + " reminders");
								xml.openParent("reminders");
								for(Reminder reminder : reminders){
									xml.openParent("reminder");
									xml.addChild("id", "" + reminder.getReminderID());
									xml.addChild("text", reminder.getReminder());
									String read = "0";
									if(reminder.isRead())
										read = "1";
									xml.addChild("read", read);
									xml.closeParent("reminder");
								}
								xml.closeParent("reminders");
							try {
								socket_writer.write(xml.getXML());
							} catch (Exception e) {
								socket_writer.write("ERR");
								e.printStackTrace();
							}
								break;
							case "old":
								break;
							case "all":
								break;
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
						reminder_dao.addReminder(database_handler, user, reminder);
						socket_writer.write("OK");
						break;
				}
				break;
		}
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
