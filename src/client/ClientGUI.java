package client;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ClientGUI {
	private JFrame frame;
	private JPanel jpanel;
	private static final String TITLE = "RemindMe Client";
	
	public ClientGUI(){
		frame = new JFrame(TITLE);
	}

	public static void main(String[] args) {
		ClientGUI client = new ClientGUI();
		client.set();
		client.setLogin();
		client.start();
	}
	
	public void start(){
		frame.setVisible(true);
	}
	public void set(){
		frame.setSize(300, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jpanel = new JPanel();
		frame.add(jpanel);
	}

	private void setLogin() {

		jpanel.setLayout(null);

		JLabel userLabel = new JLabel("User");
		userLabel.setBounds(10, 170, 80, 25);
		jpanel.add(userLabel);

		JTextField userText = new JTextField(20);
		userText.setBounds(100, 170, 160, 25);
		jpanel.add(userText);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 200, 80, 25);
		jpanel.add(passwordLabel);

		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 200, 160, 25);
		jpanel.add(passwordText);

		JButton loginButton = new JButton("login");
		loginButton.setBounds(95, 230, 80, 25);
		jpanel.add(loginButton);
		
		JButton registerButton = new JButton("register");
		registerButton.setBounds(180, 230, 80, 25);
		setRegisterActionListener(registerButton);
		jpanel.add(registerButton);
	}
	
	private void setRegisterActionListener(JButton button){
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("register clicked.");
				clearPanel();
				setRegister();
			}
		}
	);}
	
	private void verifyRegistration(JButton button){
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("register clicked.");
				JPasswordField input = (JPasswordField) e.getSource();
				System.out.println(input.getPassword());
				
			}
		}
	);}
	
	private void setRegister(){
		jpanel.setLayout(null);

		JLabel userLabel = new JLabel("username");
		userLabel.setBounds(10, 170, 80, 25);
		jpanel.add(userLabel);

		JTextField userText = new JTextField(20);
		userText.setBounds(120, 170, 180, 25);
		jpanel.add(userText);

		JLabel passwordLabel = new JLabel("password");
		passwordLabel.setBounds(10, 200, 80, 25);
		jpanel.add(passwordLabel);

		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(120, 200, 180, 25);
		jpanel.add(passwordText);
		
		JLabel passwordLabel2 = new JLabel("password (again)");
		passwordLabel2.setBounds(10, 230, 80, 40);
		passwordLabel2.setSize(200, 30);
		jpanel.add(passwordLabel2);

		JPasswordField passwordText2 = new JPasswordField(20);
		passwordText2.setBounds(120, 230, 180, 25);
		jpanel.add(passwordText2);
		
		JLabel email = new JLabel("email");
		email.setBounds(10, 260, 80, 25);
		jpanel.add(email);

		JTextField emailText = new JTextField(20);
		emailText.setBounds(120, 260, 180, 25);
		jpanel.add(emailText);
		
		JButton registerButton = new JButton("register");
		registerButton.setBounds(160, 290, 80, 20);
		verifyRegistration(registerButton);
		jpanel.add(registerButton);
	
	}
	
	private void clearPanel(){
		jpanel.removeAll();
		jpanel.updateUI();
	}

}