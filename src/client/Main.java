package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import popup.Popup;
import popup.PopupManager;
import reminder.Reminder;
import reminder.ReminderDAO;
import user.User;

public class Main extends Page{
	private GUIManager guimanager;
	private GUIRunner guirunner;
	
	public Main(GUIManager guimanager, GUIRunner guirunner){
		this.guimanager = guimanager;
		this.guirunner = guirunner;
	}

	public void load() {
		User user = guirunner.getUser();
		String username = user.getUsername();		
		
		JLabel top = new JLabel("reminders for " + username);
		top.setBounds(90, 10, 200, 25);
		guimanager.addComponent(top);
		
		int unreadCount = 0;
		int readCount = 0;
		
		System.out.println("creating reminder_commuicator");
		ReminderCommunicator reminder_communicator = new ReminderCommunicator(guirunner.getUser(), guirunner.getPassword());
		System.out.println("requesting new reminder count");
		unreadCount = reminder_communicator.getReminderCount("new");
		System.out.println("" + unreadCount + " reminders found");
		
		System.out.println("requesting read reminder count");
		readCount = reminder_communicator.getReminderCount("read");
		System.out.println("" + readCount + " reminders found");
		
		JLabel unread = new JLabel("Unread Reminders: " + unreadCount);
		unread.setBounds(1, 35, 200, 25);
		guimanager.addComponent(unread);
		
		JLabel read = new JLabel("Read Reminders:" + readCount);
		read.setBounds(1, 50, 200, 25);
		guimanager.addComponent(read);
		
		String server_status = "<font color='red'>Off</font>";
		String toggle_status_btn_text = "Turn Server On";
		if(this.guirunner.serverIsRunning()){
			server_status = "<font color='green'>Running</font>";
			toggle_status_btn_text = "Turn Server Off";
		}
		
		JLabel server_msg = new JLabel("<html>Server Status: "+ server_status +"</html>");
		server_msg.setBounds(1, 65, 200, 25);
		guimanager.addComponent(server_msg);
		
		JButton unread_btn = new JButton("Display Unread Reminders");
		unread_btn.setBounds(5, 130, 290, 25);
		setUnreadReminderActionListener(reminder_communicator, unread_btn);
		guimanager.addComponent(unread_btn);
		
		JButton toggle_server = new JButton(toggle_status_btn_text);
		toggle_server.setBounds(5, 160, 290, 25);
		setToggleServerActionListener(toggle_server);
		guimanager.addComponent(toggle_server);
	}
	
	private void setUnreadReminderActionListener(ReminderCommunicator reminder_communicator, JButton unread_btn) {
		unread_btn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Display unread buttons clicked");
				Reminder [] reminders = reminder_communicator.getReminders("new");

				if(reminders.length == 0)
					return;
				
				PopupManager popman = new PopupManager();
				for(int i = 0; i < reminders.length; i++){
					popman.createNewPopup("reminder " + reminders[i].getReminderID(), reminders[i].getReminder()).pop();
				}
			}
		}
	);}

	private void setToggleServerActionListener(JButton toggle_server) {
		toggle_server.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("toggle server clicked");
				if(!guirunner.serverIsRunning()){
					guirunner.startServer();
					ReminderClientThreadSingleton.getInstance().startService();
				}else{
					guirunner.stopServer();
					ReminderClientThreadSingleton.getInstance().stopService();
				}
				guirunner.loadMain();	
			}
		}
	);}
}
