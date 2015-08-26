package logger;

public class Logger {
	private static Logger log = null;
	int level;
	
	public Logger(int level) throws Exception{
		if(level < 0)
			throw new Exception("level must be greater than 0; found: " + level);
		this.level = level;
	}
	
	public static Logger getInstance(){
		if(log == null)
			try {
				log = new Logger(5);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return log;
	}
	
	public void log(int level,String who, String s){
		if(level <= this.level) 
			System.out.println("[" + who + "] " + s);
	}
}
