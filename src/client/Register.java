package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import registration.Registrator;

public class Register extends Page{
	private GUIManager guimanager;
	private GUIRunner guirunner;
	
	private JTextField username;
	private JPasswordField p1;
	private JPasswordField p2;
	private JTextField email;

	public Register(GUIManager guimanager, GUIRunner guirunner){
		this.guimanager = guimanager;
		this.guirunner = guirunner;
	}
	
	public void load(){

		JLabel userLabel = new JLabel("username");
		userLabel.setBounds(10, 120, 80, 25);
		this.guimanager.addComponent(userLabel);

		this.username = new JTextField(20);
		username.setBounds(120, 120, 180, 25);
		this.guimanager.addComponent(username);

		JLabel passwordLabel = new JLabel("password");
		passwordLabel.setBounds(10, 150, 80, 25);
		this.guimanager.addComponent(passwordLabel);

		this.p1 = new JPasswordField(20);
		p1.setBounds(120, 150, 180, 25);
		this.guimanager.addComponent(p1);
		
		JLabel passwordLabel2 = new JLabel("password (again)");
		passwordLabel2.setBounds(10, 180, 80, 40);
		passwordLabel2.setSize(200, 30);
		this.guimanager.addComponent(passwordLabel2);
		
		this.p2 = new JPasswordField(20);
		this.p2.setBounds(120, 180, 180, 25);
		this.guimanager.addComponent(this.p2);
		
		JLabel emailLabel = new JLabel("email");
		emailLabel.setBounds(10, 210, 80, 25);
		this.guimanager.addComponent(emailLabel);

		this.email = new JTextField(20);
		email.setBounds(120, 210, 180, 25);
		this.guimanager.addComponent(email);

		JButton loginButton = new JButton("Login Screen");
		loginButton.setBounds(60, 310, 100, 20);
		setLoginActionListener(loginButton);
		this.guimanager.addComponent(loginButton);
		
		JButton registerButton = new JButton("register");
		registerButton.setBounds(190, 310, 80, 20);
		setRegisterActionListener(registerButton);
		this.guimanager.addComponent(registerButton);
	}
	
	private void setLoginActionListener(JButton button){
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("login clicked.");
				guirunner.loadLogin();
			}
		}
	);}
	
	private void setRegisterActionListener(JButton button){
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean valid = true;
				
				if(!p1.equals(p2))
					valid = false;
				
				Registrator registrator = new Registrator(guirunner.getDatabaseHandler());
				boolean registered = registrator.registerUser(username.getText(), email.getText(), new String(p1.getPassword()), new String(p2.getPassword()));
				if(registered)
					guirunner.loadLogin();
				else
					guirunner.loadRegister();
			}
		}
	);}
}
