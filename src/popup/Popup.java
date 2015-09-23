package popup;

import java.awt.Dimension;
import java.util.Random;

import javax.swing.*;

public class Popup extends Thread{
	private String title;
	private String text;
	
	private JFrame jframe;
	private JPanel jpanel;
	
	public Popup(){
		this.jframe = null;
		this.jpanel = null;
		this.title = null;
		this.text = null;
	}
	
	public Popup(String text){
		this.jframe = null;
		this.jpanel = null;
		this.title = null;
		this.text = text;
	}
	
	public Popup(String title, String text){
		this.jframe = null;
		this.jpanel = null;
		this.title = title;
		this.text = text;
	}
	
	public void run(){
		
	}
	
    public void pop() {
    	if (this.text == null)
    		return;
        //Create and set up the window.
        this.jframe = new JFrame(this.title);
        this.jframe.setSize(200, 200);
        this.jpanel = new JPanel();
        this.jpanel.setLayout(null);
        this.jframe.add(this.jpanel);
        
        Random rand = new Random();
        int ran1 = rand.nextInt(1000);
        int ran2 = rand.nextInt(1000);
        this.jframe.setLocation(ran1, ran2);

        JLabel label = new JLabel(String.format("<html><div style=\"width:%dpx;\">%s</div><html>", 190, this.text));
        //JLabel label = new JLabel(this.text);
        //JTextArea label = new JTextArea(this.text);
        label.setBounds(5, 5, 180, 25);
        this.jpanel.add(label);
        
        JButton mark_as_read = new JButton("Mark As Read");
        mark_as_read.setBounds(5, 30, 180, 25);
        this.jpanel.add(mark_as_read);

        //Display the window.
        jframe.setVisible(true);
    }
    
    public void setTitle(String title){
    	this.title = title;
    }

	public void setText(String text) {
		this.text = text;	
	}

	public void setVisible(boolean b) {
		this.jframe.setVisible(b);
	}
	
	public void unpop(){
		this.jframe.dispose();
	}
}
