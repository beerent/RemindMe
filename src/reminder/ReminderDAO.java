package reminder;

import java.util.ArrayList;

import database.DatabaseHandler;
import database.QueryResult;
import user.User;
import user.UserDAO;

public class ReminderDAO {
	private DatabaseHandler database_handler;
	
	public ReminderDAO(DatabaseHandler database_handler){
		this.database_handler = database_handler;
	}
	
	public ArrayList<Reminder> getReminders(User user, String option){
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
	
	public void addReminder(User user, String reminder){
		String sql = "insert into reminders (user_id, reminder) values (" + user.getUserID() + ", '"+ reminder +"')";
		database_handler.executeInsert(sql);
	}
	
	public void markReminderAsRead(User user, Reminder reminder, int read){
		String sql = "update reminders set is_read = "+ read +" where reminder_id = " + reminder.getReminderID();
		database_handler.executeInsert(sql);
	}

	public int getReminderCount(User user, String count_op) {
		String op = "";
		if(count_op.equals("new"))
			op = " and is_read = 0";
		else if(count_op.equals("read"))
			op = " and is_read = 1";
		String sql = "select count(*) as total from reminders where user_id = " + user.getUserID() + op;
		QueryResult result = database_handler.executeQuery(sql);
		return Integer.parseInt(result.getElement(0, 0));
	}
}
