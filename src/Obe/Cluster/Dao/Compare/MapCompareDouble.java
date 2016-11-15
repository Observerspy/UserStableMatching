package Obe.Cluster.Dao.Compare;

import java.util.Comparator;
import java.util.Map;

import Obe.Cluster.Dto.UserProb;

public class MapCompareDouble implements Comparator<Map.Entry<String, Double>>{

	@Override
	public int compare(Map.Entry<String, Double> mp1, Map.Entry<String, Double> mp2) {
		if(mp2.getValue() - mp1.getValue()>0)
			return 1;
		else if(mp2.getValue() - mp1.getValue()<0)
			return -1;
		else
			return 0;
	}

}
