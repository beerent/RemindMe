package database;

import java.util.ArrayList;

import reminder.Reminder;

public class DatabaseWrapper {
	private DatabaseHandler database_handler;
	
	public DatabaseWrapper(){
		this.database_handler = new DatabaseHandler();
	}
	
	public int getUserID(String user_name){
		String sql = "select user_id from users where username = '"+ user_name +"'";
		QueryResult result = database_handler.executeQuery(sql);
		if (result.containsData())
			return Integer.parseInt(result.getElement(0, 0));
		return -1;
	}
	
	public ArrayList<Reminder> getReminders(int user_id){
		ArrayList<Reminder> reminders = new ArrayList<Reminder>();
		
		String sql = "select reminder, reminder_id, is_read from reminders where user_id = " + user_id;
		System.out.println(sql);
		QueryResult result = database_handler.executeQuery(sql);
		if(!result.containsData())
			return null;
		for(int i = 0; i < result.numRows(); i++){
			String reminder = result.getElement(i, 0);
			int reminder_id = Integer.parseInt(result.getElement(i,  1));
			int temp_read = Integer.parseInt(result.getElement(i,  2));
			
			boolean read;
			if(temp_read == 0)
				read = false;
			else
				read = true;
			
			reminders.add(new Reminder(reminder, reminder_id, read));
		}
		return reminders;	
	}
}
