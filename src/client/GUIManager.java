package client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class GUIManager {
	private JFrame jframe;
	private JPanel jpanel;
	
	private static final String TITLE = "RemindMe Client";
	
	public GUIManager(){
		jframe = new JFrame(TITLE);
		jframe.setSize(300, 500);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jpanel = new JPanel();
		setLayout();
		jframe.add(jpanel);
		
	}
	
	public void hide(){
		this.jframe.setVisible(false);
	}
	
	public void appear(){
		this.jframe.setVisible(true);
	}
	
	public void clear(){
		this.jpanel.removeAll();
		this.jpanel.updateUI();
	}
	
	public void addComponent(Component component){
		this.jpanel.add(component);
	}

	public void setLayout(){
		this.jpanel.setLayout(null);
	}
}