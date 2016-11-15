package Obe.Cluster.Dao.Compare;

import java.util.Comparator;
import java.util.Map;

import Obe.Cluster.Dto.UserProb;

public class MapCompareUserProb implements Comparator<UserProb>{

	@Override
	public int compare(UserProb up1, UserProb up2) {
		if(up2.getP() - up1.getP()>0)
			return 1;
		else if(up2.getP() - up1.getP()<0)
			return -1;
		else
			return 0;
	}

}
