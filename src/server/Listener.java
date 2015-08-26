package server;

import java.net.Socket;
import java.util.ArrayList;

import logger.Logger;
import socketio.SocketReader;
import socketio.SocketWriter;

public class Listener extends Thread{
	private Logger log;
	private Socket socket;
	private static boolean run;

	public Listener(Socket socket) {
		Listener.run = false;
		this.socket = socket;
		this.log = Logger.getInstance();
	}
	

	public static void setRun(boolean run){
		Listener.run = run;
	}
	
	public void run(){
		SocketReader socket_reader = new SocketReader(socket);
		SocketWriter socket_writer = new SocketWriter(socket);
		
		String user = socket_reader.read();
		log(2, "user: " + user + " connected");
		socket_writer.write("OK");
		log(4, "responded OK to user");
		
		ArrayList<String> request = new ArrayList<String>();
		String option = socket_reader.read();
		while(!option.equals( "^]")){
			log(4, "adding: " + option);
			request.add(option);
			option = socket_reader.read();		
		}
		
		int i = 0;
		switch(request.get(i++)){
			case "get":
				log(4, "get");
				switch(request.get(i++)){
					case "updates":
						log(4, "updates");
						socket_writer.write("1");
						break;
					case "reminders":
						log(4, "reminders");
						switch(request.get(i++)){
							case "new":
								log(4, "new");
								socket_writer.write("new");
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
	
	private void log(int level, String log){
		this.log.log(level, "LISTENER", log);
	}
}
