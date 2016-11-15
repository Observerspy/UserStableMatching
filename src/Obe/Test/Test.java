package Obe.Test;

import java.util.ArrayList;
import java.util.List;

import Obe.Cluster.Dto.User;

public class Test {

	public static void main(String[] args) {
		//非基本类型都是浅拷贝
		List<User> perfer = new ArrayList<User>();
		// TODO Auto-generated method stub
		User user1 = new User();user1.setName("a");perfer.add(user1);
		User user2 = new User();user2.setName("b");perfer.add(user2);
		User user3 = new User();user3.setName("c");perfer.add(user3);
		User user4 = new User();user4.setName("d");perfer.add(user4);
		User user5 = new User();user5.setName("e");perfer.add(user5);

		for(int i=0;i<perfer.size();i++){
			System.out.print(perfer.get(i).getName());
		}
		System.out.println();
		System.out.println("===========");
		

		List<User> perfer2 = perfer;
		perfer2.remove(2);
		//User u = perfer.get(2);
		//u.setName("x");
		for(int i=0;i<perfer.size();i++){
			System.out.print(perfer2.get(i).getName());
		}
	}

}
