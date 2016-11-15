package Obe.Cluster.Dao.Compare;

import java.util.Comparator;
import java.util.Map;

public class MapCompare implements Comparator<Map.Entry<String, Integer>>{

	@Override
	public int compare(Map.Entry<String, Integer> mp1, Map.Entry<String, Integer> mp2) {
		if(mp2.getValue() - mp1.getValue()>0)
			return 1;
		else if(mp2.getValue() - mp1.getValue()<0)
			return -1;
		else
			return 0;
	}

}
