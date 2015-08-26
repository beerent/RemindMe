package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import logger.Logger;

public class RemindMeServer {
	
	public static void main(String [] args){
		Logger log = null;
		String who = "SERVER";
		
		try {
			log = Logger.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ServerSocket ss;
		try {
			log.log(0, who, "server socket created");
			ss = new ServerSocket(1313);
			while(true){
				log.log(1, who, "listening for client...");
				Socket socket = ss.accept();
				new Listener(socket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}