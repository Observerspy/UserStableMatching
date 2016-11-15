package Obe.Cluster.Dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Obe.Cluster.Dto.Pair;
import Obe.Cluster.Dto.User;

public class StableMatch {//标准SM(I)
	//用于辅助记录pair的关系，其中pair(a,b)将分别记录a--b与b--a 
	HashMap<User,User> map = new HashMap<>();
	//用于辅助记录pair的关系，其中pair(a,b)将记录为a.name+b.name--pair(a,b)
	//HashMap<String,Pair> table = new HashMap<>();
	
	public HashMap<User,User> StartMatching(List<User>A){
		for(int i=0;i<A.size();i++){
			User user1 = A.get(i);
			if(user1.getFlag()==0&&user1.getPerfer().size()!=0){
				System.out.println(user1.getName()+":");
				List<User> per = user1.getPerfer();
				User user2 = per.get(0);//get top perfer 
				per.remove(0);//user1.setPerfer(per);//remove top 
				
				if(user1.getName().equals(user2.getName())){//自链接,有必要么？
					//set a and a dating 
					map.put(user1, user2);map.put(user2, user1);
					//update user1\2's status;
					user1.setFlag(1);user2.setFlag(1);
					System.out.println(user1.getName()+"---"+user2.getName());
				}
				else {
					if(user2.getFlag()==0){//约会
						if(user2.getPerfer().contains(user1)){//如果b的perfer中有a，即b不拒绝a
							//set a and a dating 
							map.put(user1, user2);map.put(user2, user1);
							//update user1\2's status;
							user1.setFlag(1);user2.setFlag(1);
							System.out.println("date"+user1.getName()+"---"+user2.getName());
						}
					}
					else{//b有约会对象
						User user3 = map.get(user2);//获取b的dating对象 
						List<User> per2 = user2.getPerfer();
						int a = Integer.MAX_VALUE;
						if(per2.contains(user1))
							a = per2.indexOf(user1);//b对a的好感顺位,a为MAX时即表示拒绝
						int c = per2.indexOf(user3);//b对当前的对象好感顺位
						if(a<c){//a靠前
							//update user3's status and remove
							user3.setFlag(0);
							map.remove(user2);map.remove(user3);
							//set a and a dating 
							map.put(user1, user2);map.put(user2, user1);
							//update user1\2's status;
							user1.setFlag(1);user2.setFlag(1);
							System.out.println("change"+user1.getName()+"---"+user2.getName());
						}
					}
				}
			}
			if((i+1)==A.size()&&ExistFree(A)){//如果存在free进行下一轮
				i=-1;
			}
		}
		
		return map;
	}
	
	private boolean ExistFree(List<User>A){
		for(int i=0;i<A.size();i++){
			if(A.get(i).getFlag()==0&&A.get(i).getPerfer().size()!=0)
				return true;
		}
		return false;		
	}
}
