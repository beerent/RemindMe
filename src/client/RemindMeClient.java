package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import socketio.SocketReader;
import socketio.SocketWriter;

public class RemindMeClient {
	private static final int server_port = 1313;
	private static final String server_address = "localhost";
	private SocketReader socket_reader;
	private SocketWriter socket_writer;
	
	public RemindMeClient(){
		socket_reader = null;
		socket_writer = null;
		
	}
	
	public static void main(String[] args) {
		RemindMeClient client = new RemindMeClient();
		int i = 1;
		while(i == 1){
			boolean has_updates = client.checkForUpdates();
			if(has_updates){
				client.getAndDisplayNewReminders();
			}		
			i++;
			//wait 20 seconds
		}
	}
	
	private boolean authenticate() {
		this.socket_writer.write("beerent");
		String result = this.socket_reader.read();
		return result.equals("OK");
	}

	//returns true if server responds with a 1, aka this client has updates
	private boolean checkForUpdates(){
		connect();
		this.socket_writer.write("get");
		this.socket_writer.write("updates");
		this.socket_writer.write("^]");
		int result = Integer.parseInt(this.socket_reader.read());
		disconnect();
		return result == 1;
	}
	
	//request all new reminders from server for this client
	private void getAndDisplayNewReminders(){
		connect();
		this.socket_writer.write("get");
		this.socket_writer.write("reminders");
		this.socket_writer.write("new");
		this.socket_writer.write("^]");
		String xml_reminders = this.socket_reader.read();
		System.out.println("reminders: " + xml_reminders);
		disconnect();
	}
	
	//set IO objects connected to socket
	private Socket connect(){
		try {
			Socket socket = new Socket(server_address, server_port);
			this.socket_reader = new SocketReader(socket);
			this.socket_writer = new SocketWriter(socket);
			socket_writer.write("beerent");
			String ok = socket_reader.read();
			if (ok.equals("OK"))
				return socket;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//set IO objects to null
	private void disconnect(){
		this.socket_reader = null;
		this.socket_writer = null;
	}
}
