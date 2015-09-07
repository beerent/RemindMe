package reminder;

import java.util.ArrayList;

import database.DatabaseHandler;
import database.QueryResult;
import user.User;
import user.UserDAO;

public class ReminderDAO {
	private static ReminderDAO instance = null;
	
	private ReminderDAO(){}
	
	public static ReminderDAO getInstance() {
		if(instance == null)
			instance = new ReminderDAO();
		return instance;
	}
	
	public ArrayList<Reminder> getReminders(DatabaseHandler database_handler, User user){
		ArrayList<Reminder> reminders = new ArrayList<Reminder>();
		
		String sql = "select reminder, reminder_id, is_read from reminders where user_id = " + user.getUserID();
		QueryResult result = database_handler.executeQuery(sql);

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
	
	public void addReminder(DatabaseHandler database_handler, User user, String reminder){
		String sql = "insert into reminders (user_id, reminder) values (" + user.getUserID() + ", '"+ reminder +"')";
		database_handler.executeInsert(sql);
	}
}
