package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import user.User;
import user.UserAuthenticator;

public class Login extends Page{
	private GUIManager guimanager;
	private GUIRunner guirunner;
	private boolean denied;
	
	public Login(GUIManager guimanager, GUIRunner guirunner){
		this.guimanager = guimanager;
		this.guirunner = guirunner;
		this.denied = false;
	}

	public void load() {
		
		if(this.denied){
			JLabel deniedLabel = new JLabel("invalid username/ password combo, try again.");
			deniedLabel.setBounds(5, 130, 300, 25);
			guimanager.addComponent(deniedLabel);
			this.denied = false;
		}

		JLabel userLabel = new JLabel("User");
		userLabel.setBounds(10, 170, 80, 25);
		guimanager.addComponent(userLabel);

		JTextField userText = new JTextField(20);
		userText.setBounds(100, 170, 160, 25);
		guimanager.addComponent(userText);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 200, 80, 25);
		guimanager.addComponent(passwordLabel);

		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 200, 160, 25);
		guimanager.addComponent(passwordText);

		JButton loginButton = new JButton("login");
		loginButton.setBounds(95, 230, 80, 25);
		setLoginActionListener(loginButton, userText, passwordText);
		guimanager.addComponent(loginButton);
		
		JButton registerButton = new JButton("register");
		registerButton.setBounds(180, 230, 80, 25);
		setRegisterActionListener(registerButton);
		guimanager.addComponent(registerButton);
	}
	
	private void setLoginActionListener(JButton button, JTextField usernameField, JPasswordField passwordField){
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("login clicked.");
				String username = usernameField.getText();
				String password = new String (passwordField.getPassword());		
				System.out.println("trying: " + username + " - " + password);
				UserCommunicator user_communicator = new UserCommunicator();
				User user = user_communicator.authenticateUser(username, password);
				if(user != null){
					System.out.println("client " + username + " logged in");
					guirunner.setUser(user);
					guirunner.setPassword(password);
					guirunner.loadMain();
				}else{
					denied = true;
					guirunner.loadLogin();
				}
			}
		}
	);}
	
	private void setRegisterActionListener(JButton button){
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("register clicked.");
				guirunner.loadRegister();
			}
		}
	);}
}