package client;

import java.util.Scanner;

public class tester {
	public static void main(String[] args) {
		ReminderClientThreadSingleton test = ReminderClientThreadSingleton.getInstance();
		test.start();
		Scanner sc = new Scanner(System.in);
		System.out.println("enter a string:");
		String s = sc.nextLine();
		while(!s.equals("quit")){
			if(s.equals("start")){
				System.out.println("got start");
				test.startService();
			}
			if(s.equals("stop")){
				System.out.println("got stop");
				test.stopService();
			}
			s = sc.nextLine();
		}
	}
}
