package client;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import reminder.Reminder;
import user.User;

public class ReminderCommunicator {
	private ClientSocket client_socket;
	private User user;
	private String pass;
	
	public ReminderCommunicator(User user, String pass){
		this.user = user;
		this.pass = pass;
	}
	
	public Reminder [] getReminders(String read){
		login();
		client_socket.write("get");
		client_socket.write("reminders");
		client_socket.write(read); 
		client_socket.write("^]");
		String unread_reminders_json = client_socket.read();
		ArrayList<Reminder> reminders = new ArrayList<Reminder>();
		try {
			JSONObject json = new JSONObject(unread_reminders_json);
			JSONArray json_array = json.getJSONArray("reminder");
			
			for(int i = 0; i < json_array.length(); i++){
				JSONObject temp_json = json_array.getJSONObject(i);
				String r = temp_json.getString("read");
				int in = temp_json.getInt("id");
				String text = temp_json.getString("text");
				
				reminders.add(new Reminder(text, in, r.equals("1")));	
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Reminder [] reminder_array = new Reminder[reminders.size()];
		reminder_array = reminders.toArray(reminder_array);
		return reminder_array;
	}
	
	public int getReminderCount(String read){	
		login();
		client_socket.write("get");
		client_socket.write("reminder_count");
		client_socket.write(read); 
		client_socket.write("^]");
		String count_xml = client_socket.read();
		return Integer.parseInt(count_xml.substring(count_xml.indexOf("<count>")+7, count_xml.indexOf("</count>")));
	}
	
	private void login(){
		this.client_socket = new ClientSocket("localhost", 1313);
		client_socket.write("login");
		client_socket.read();
		client_socket.write(user.getUsername());
		client_socket.write(pass);
		String auth = client_socket.read();
		if(!auth.equals("OK")){
			System.exit(1);
		}
	}
}
