package Obe.Cluster.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Obe.Cluster.Dao.StableMatchEX;
import Obe.Cluster.Dto.Pair;
import Obe.Cluster.Dto.User;

public class MainPromotedEX {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<User> A = new ArrayList<User>();//from DB
		List<User> B = new ArrayList<User>();//from DB
		
		//用户人数不等，向少的一方添加虚假用户，参与计算但不输出结果???有必要么???
		User faker = new User();
		faker.setName(">>FAKER<<");
		int key = 0;
		if(A.size()<B.size()){
			for(int i=0;i<B.size()-A.size();i++){
				A.add(faker);//let A as boys
			}
		}
		else{
			key = 1;
			for(int i=0;i<A.size()-B.size();i++){
				B.add(faker);//let B as boys
			}
		}
		//根据信息计算perfer值并排序

		//将perfer值分别填入各自的perferlist中，并只保留perfer>K的对象

		//根据先验知识将acquaint分别填入各自的acquaintancelist中
		
		//do stable matching
		StableMatchEX sm = new StableMatchEX();
		HashMap<User,User> map = new HashMap<>();
		if(key==0)
			map = sm.StartMatching(A);
		else
			map = sm.StartMatching(B);
		
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
		Iterator<Entry<String, Pair>> iter1 = show.entrySet().iterator();
		while (iter1.hasNext()) {
			Map.Entry entry = (Map.Entry) iter1.next();
			String name = (String) entry.getKey();
			Pair p = (Pair) entry.getValue();
			System.out.println(name);
		}
	}

}
