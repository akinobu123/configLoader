package study;

import java.util.ArrayList;
import java.util.HashMap;

public class matrix {
	ArrayList<HashMap<String,String>> fMatrix = 
			new ArrayList<HashMap<String,String>>();
	
	public void add(HashMap<String,String> map) {
		fMatrix.add(map);
	}
	
	public String get(int index, String key) {
		return fMatrix.get(index).get(key);
	}
}
