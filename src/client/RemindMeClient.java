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
		client.addReminder();
		client.getAndDisplayNewReminders();
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
	
	private void addReminder(){
		connect();
		this.socket_writer.write("add");
		this.socket_writer.write("reminder");
		this.socket_writer.write("this is a test reminder");
		this.socket_writer.write("^]");
		disconnect();
	}
	
	//request all new reminders from server for this client
	private void getAndDisplayAllReminders(){
		connect();
		this.socket_writer.write("get");
		this.socket_writer.write("reminders");
		this.socket_writer.write("all");
		this.socket_writer.write("^]");
		String xml_reminders = this.socket_reader.read();
		System.out.println("reminders: " + xml_reminders);
		disconnect();
	}
	
	//request all new reminders from server for this client
	private void getAndDisplayOldReminders(){
		connect();
		this.socket_writer.write("get");
		this.socket_writer.write("reminders");
		this.socket_writer.write("old");
		this.socket_writer.write("^]");
		String xml_reminders = this.socket_reader.read();
		System.out.println("reminders: " + xml_reminders);
		disconnect();
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
			socket_writer.write("password1");
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
