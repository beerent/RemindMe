package server;

import java.net.Socket;
import java.util.ArrayList;

import database.DatabaseWrapper;
import logger.Logger;
import reminder.Reminder;
import socketio.SocketReader;
import socketio.SocketWriter;

public class Listener extends Thread{
	private Logger log;
	private Socket socket;
	private static boolean run;
	private DatabaseWrapper database_wrapper;
	
	private boolean quit;

	public Listener(Socket socket) {
		Listener.run = false;
		this.socket = socket;
		this.log = Logger.getInstance();
		this.database_wrapper = new DatabaseWrapper();
		this.quit = false;
	}
	

	public static void setRun(boolean run){
		Listener.run = run;
	}
	
	public void run(){
		SocketReader socket_reader = new SocketReader(socket);
		SocketWriter socket_writer = new SocketWriter(socket);
		
		String user = socket_reader.read();
		int user_id = this.database_wrapper.getUserID(user);
		if(user_id == -1)
			invalidUser(user);
		
		if(this.quit)
			return;
		
		
		serverLog(2, "user: " + user + " (" + user_id + ") connected.");
		socket_writer.write("OK");
		serverLog(4, "responded OK to user");
		
		ArrayList<String> request = new ArrayList<String>();
		String option = socket_reader.read();
		while(!option.equals( "^]")){
			serverLog(4, "adding: " + option);
			request.add(option);
			option = socket_reader.read();		
		}
		
		int i = 0;
		switch(request.get(i++)){
			case "get":
				serverLog(4, "get");
				switch(request.get(i++)){
					case "updates":
						serverLog(4, "updates");
						socket_writer.write("1");
						break;
					case "reminders":
						serverLog(4, "reminders");
						switch(request.get(i++)){
							case "new":
								ArrayList<Reminder> reminders = this.database_wrapper.getReminders(user_id);
								for(Reminder rem : reminders)
									System.out.println("REMINDER: " + rem.getReminder());
								break;
							case "old":
								break;
							case "all":
								break;
						}
						break;
				}		
				break;
			default:
		}
	}
	
	private void invalidUser(String user){
		userLog(0, user, "user: " + user + " does not exist.");
		this.quit = true;
	}
	
	private void userLog(int level, String user, String log){
		this.log.log(level, "[ " + user + "] " + "LISTENER", log);
	}
	private void serverLog(int level, String log){
		this.log.log(level, "LISTENER", log);
	}
}
