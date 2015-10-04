package client;

import org.json.JSONException;
import org.json.JSONObject;

import user.User;

public class UserCommunicator {
	private static final int server_port = 1313;
	private static final String server_address = "72.182.73.183";
	private ClientSocket client_socket;
	
	public UserCommunicator(){
		client_socket = new ClientSocket(server_address, server_port);
	}

	public User authenticateUser(String username, String password) {
		client_socket.write("auth");
		client_socket.read();
		client_socket.write(username);
		client_socket.write(password);
		String response = client_socket.read();
		
		if(response.equals("denied"))
			return null;

		JSONObject json;
		try {
			json = new JSONObject(response);
			int user_id = json.getInt("user_id");
			return new User(username, user_id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
