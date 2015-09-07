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
	
	public ArrayList<Reminder> getReminders(DatabaseHandler database_handler, User user, String option){
		ArrayList<Reminder> reminders = new ArrayList<Reminder>();
		
		String sql = null;
		if(option.equals("new"))
			sql = "select reminder, reminder_id, is_read from reminders where user_id = " + user.getUserID() + " and is_read = " + 0;
		if(option.equals("read"))
			sql = "select reminder, reminder_id, is_read from reminders where user_id = " + user.getUserID() + " and is_read = " + 1;
		if(option.equals("all"))
			sql = "select reminder, reminder_id, is_read from reminders where user_id = " + user.getUserID();
		
		if(sql == null)
			return null;
		
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
	
	public void markReminderAsRead(DatabaseHandler database_handler, User user, Reminder reminder, int read){
		String sql = "update reminders set is_read = "+ read +" where reminder_id = " + reminder.getReminderID();
		database_handler.executeInsert(sql);
	}
}
