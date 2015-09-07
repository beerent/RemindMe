package xml;

public class XMLWriter {
	private String xml;
	private boolean open;
	
	public XMLWriter(){
		this.xml = "";
		this.open = false;
	}
	
	public void openParent(String parent){
		this.xml += "<" + parent + ">";
		this.open = true;
	}
	
	public void closeParent(String parent){
		this.xml += "</" + parent + ">";
		this.open = false;
	}
	
	public void addChild(String tag, String element){
		this.xml += "<" + tag + ">";
		this.xml += element;
		this.xml += "</" + tag + ">";
	}
	
	public String getXML() throws Exception{
		if(this.open)
			throw new Exception ("XML is still open");
		return this.xml;
	}
}
