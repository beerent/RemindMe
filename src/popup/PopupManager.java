package popup;

public class PopupManager {

	public PopupManager(){
		
	}
	
	public static void main(String[] args) {
		PopupManager pm = new PopupManager();
		Popup popup = pm.createNewPopup("title", "text");
		popup.pop();
	}
	
	public Popup createNewPopup(String title, String text){
		return new Popup(title, text);
	}
}