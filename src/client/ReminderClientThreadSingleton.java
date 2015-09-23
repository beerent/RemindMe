package client;

public class ReminderClientThreadSingleton extends Thread{
	private static boolean running = false;
	private static ReminderClientThreadSingleton instance = null;
	
	
	private ReminderClientThreadSingleton(){}

	public static ReminderClientThreadSingleton getInstance(){
		if(instance == null){
			instance = new ReminderClientThreadSingleton();
			instance.start();
		}
		return instance;
	}
	
	public void startService(){
		log("starting service");
		ReminderClientThreadSingleton.running = true;
	}
	
	public void stopService(){
		log("stopping service");
		ReminderClientThreadSingleton.running = false;
	}
	
	public void run(){
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			while(ReminderClientThreadSingleton.running){
				System.out.println("running...");
				try {
					currentThread().sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void log(String log){
		System.out.println("SINGLETON: " + log);
	}
}
