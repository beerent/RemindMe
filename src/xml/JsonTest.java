package xml;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonTest {
	public static void main(String[] args) {
		JSONObject o1 = new JSONObject();
		JSONObject o2 = new JSONObject();
		JSONObject o3 = new JSONObject();
		JSONArray a1 = new JSONArray();
		try {
			o2.put("text", "this is reminder 1");
			o2.put("id", 1);
			
			o3.put("text", "this is reminder 2");
			o3.put("id", 2);
			
			a1.put(o2);
			a1.put(o3);
			o1.put("reminder", a1);
			
			System.out.println(o1);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
