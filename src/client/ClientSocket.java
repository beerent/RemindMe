package client;

import java.io.IOException;
import java.net.Socket;

import socketio.SocketReader;
import socketio.SocketWriter;

public class ClientSocket {
	private Socket socket;
	private SocketWriter socket_writer;
	private SocketReader socket_reader;
	
	public ClientSocket(String host, int port){
		try {
			socket = new Socket(host, port);
			this.socket_reader = new SocketReader(socket);
			this.socket_writer = new SocketWriter(socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void write(String s){
		this.socket_writer.write(s);
	}
	
	public String read(){
		return this.socket_reader.read();
	}
}
