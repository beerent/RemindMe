package reminder;

public class Reminder {
	private String reminder;
	private boolean read;
	private int reminder_id;
	
	public Reminder(int reminder_id){
		this.reminder_id = reminder_id;
	}
	
	public Reminder(String reminder, int reminder_id, boolean read){
		this.reminder = reminder;
		this.read = read;
		this.reminder_id = reminder_id;
	}
	
	public String getReminder(){
		return reminder;
	}
	
	public boolean isRead(){
		return read;
	}
	
	public int getReminderID(){
		return reminder_id;
	}
	
	public String toString(){
		String read = "unread";
		if(this.read)
			read = "read";
		return "" + reminder_id + " (" + read + "): " + reminder;
	}
}
