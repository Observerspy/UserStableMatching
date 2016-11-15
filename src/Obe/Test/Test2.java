package Obe.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Obe.Cluster.Dao.StableMatch;
import Obe.Cluster.Dao.StableMatchEX;
import Obe.Cluster.Dto.Pair;
import Obe.Cluster.Dto.User;

public class Test2 {
	//验证GS算法正确性
	public static void main(String[] args) {
		//girls
		User user1 = new User();user1.setName("1");
		User user2 = new User();user2.setName("2");
		User user3 = new User();user3.setName("3");
		User user4 = new User();user4.setName("4");
		User user5 = new User();user5.setName("5");
		//boys
		User usera = new User();usera.setName("A");
		User userb = new User();userb.setName("B");
		User userc = new User();userc.setName("C");
		User userd = new User();userd.setName("D");
		User usere = new User();usere.setName("E");
		//boys list
		usera.getPerfer().add(user1);usera.getPerfer().add(user3);//usera.getPerfer().add(user2);usera.getPerfer().add(user1);usera.getPerfer().add(user3);
		userb.getPerfer().add(user1);userb.getPerfer().add(user2);//userb.getPerfer().add(user3);userb.getPerfer().add(user4);userb.getPerfer().add(user1);
		userc.getPerfer().add(user2);//userc.getPerfer().add(user2);userc.getPerfer().add(user1);//userc.getPerfer().add(user4);userc.getPerfer().add(user3);
		//userd.getPerfer().add(user1);userd.getPerfer().add(user2);userd.getPerfer().add(user4);//userd.getPerfer().add(user3);userd.getPerfer().add(user1);
		//usere.getPerfer().add(user2);usere.getPerfer().add(user5);usere.getPerfer().add(user1);usere.getPerfer().add(user4);usere.getPerfer().add(user3);
		
		//grils list
		user1.getPerfer().add(userb);user1.getPerfer().add(usera);//user1.getPerfer().add(userd);user1.getPerfer().add(userb);//user1.getPerfer().add(userc);
		user2.getPerfer().add(userb);user2.getPerfer().add(userc);//user2.getPerfer().add(usere);user2.getPerfer().add(userb);user2.getPerfer().add(usera);
		user3.getPerfer().add(usera);//user3.getPerfer().add(usera);user3.getPerfer().add(userb);//user3.getPerfer().add(userb);user3.getPerfer().add(userc);
		//user4.getPerfer().add(userc);user4.getPerfer().add(userb);//user4.getPerfer().add(userd);user4.getPerfer().add(usera);user4.getPerfer().add(userc);
		//user5.getPerfer().add(userc);user5.getPerfer().add(usere);user5.getPerfer().add(userb);user5.getPerfer().add(userd);user5.getPerfer().add(usera);
		
		//girls acq list
		user1.getAcquaintance().add(usera);
		user2.getAcquaintance().add(userb);
		user3.getAcquaintance().add(usera);
		
		List<User> A = new ArrayList<User>();
		A.add(usera);A.add(userb);A.add(userc);A.add(userd);//A.add(usere);
		
		//StableMatchEX sm = new StableMatchEX();
		StableMatch sm = new StableMatch();
		HashMap<User,User> map = sm.StartMatching(A);
		
		//showing results
		HashMap<String,Pair> show = new HashMap<>();
		Iterator<Entry<User, User>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			User u1 = (User) entry.getKey();
			User u2 = (User) entry.getValue();
			Pair p = new Pair();
			p.setU1(u1);p.setU2(u2);
			String str = u1.getName()+"---"+u2.getName();String str2 = u2.getName()+"---"+u1.getName();
			if(!show.containsKey(str)&&!show.containsKey(str2)){
				show.put(str, p);
			}
		}
		//print
		System.out.println("==========RESULT:============");
		Iterator<Entry<String, Pair>> iter1 = show.entrySet().iterator();
		while (iter1.hasNext()) {
			Map.Entry entry = (Map.Entry) iter1.next();
			String name = (String) entry.getKey();
			Pair p = (Pair) entry.getValue();
			System.out.println(name);
		}
	}
}
