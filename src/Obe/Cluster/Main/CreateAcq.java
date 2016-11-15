package Obe.Cluster.Main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import Obe.Cluster.Dto.Pair;
import Obe.Util.LoadBigFile;

public class CreateAcq {
	private static String filepath = "D:/Work/UserStableMatching/etc/SVM/";

	public static void main(String[] args) throws Exception {
		LoadBigFile load = new LoadBigFile();
		load.setPath(filepath+"1000_error.txt");
		String text = load.Load();
		String t = "";
		String[] texts = text.split("\r\n");
		HashMap<String,Integer> m = new HashMap<String,Integer>();
		for(String s:texts){
			String[] str = s.split("---");
			str[0] = str[0].split("--")[0];
			str[1] = str[1].split("--")[0];

			Integer counter = m.get(str[0]);
			if (counter == null) 
				counter = 1;
		    else 
				counter++; 
			m.put(str[0], counter);
			
			counter = m.get(str[1]);
			if (counter == null) 
				counter = 1;
		    else 
				counter++; 
			m.put(str[1], counter);
		}
		
		Iterator<Entry<String, Integer>> iter = m.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String name = (String) entry.getKey();
			Integer p = (Integer) entry.getValue();
			if(p>1)
				t += name+",";
		}
		
		System.out.println(t);
	}

}
