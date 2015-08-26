package popup;

import java.awt.Dimension;

import javax.swing.*;

public class Popup extends Thread{
	private String title;
	private String text;
	
	JFrame frame;
	
	public Popup(){
		this.frame = null;
		this.title = null;
		this.text = null;
	}
	
	public Popup(String text){
		this.frame = null;
		this.title = null;
		this.text = text;
	}
	
	public Popup(String title, String text){
		this.frame = null;
		this.title = title;
		this.text = text;
	}
	
	public void run(){
		
	}
	
    public void pop() {
    	if (this.text == null)
    		return;
        //Create and set up the window.
        this.frame = new JFrame(this.title);

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel(this.text);
        JButton button = new JButton("button a");
        button.setPreferredSize(new Dimension(20, 40));
        
        frame.getContentPane().add(label);
        frame.getContentPane().add(button);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    public void setTitle(String title){
    	this.title = title;
    }

	public void setText(String text) {
		this.text = text;	
	}

	public void setVisible(boolean b) {
		this.frame.setVisible(b);
	}
	
	public void unpop(){
		this.frame.dispose();
	}
}
