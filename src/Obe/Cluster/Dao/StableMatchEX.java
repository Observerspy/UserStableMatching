package Obe.Cluster.Dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Obe.Cluster.Dto.Pair;
import Obe.Cluster.Dto.User;

public class StableMatchEX {//引入先验知识的SMI
	//用于辅助记录pair的关系，其中pair(a,b)将分别记录a--b与b--a 
	HashMap<User,User> map = new HashMap<>();
	//用于辅助记录pair的关系，其中pair(a,b)将记录为a.name+b.name--pair(a,b)
	//HashMap<String,Pair> table = new HashMap<>();
	
	public HashMap<User,User> StartMatching(List<User>A){
		for(int i=0;i<A.size();i++){
			User user1 = A.get(i);
			if(user1.getFlag()==0&&user1.getPerferIndex()<user1.getPerfer().size()){
				System.out.println(user1.getName()+":");
				List<User> per = user1.getPerfer();
				int index = user1.getPerferIndex();
				User user2 = per.get(index);//get top index perfer 
				
				if(index<per.size())//两者相等说明index已指向最后一个
					user1.setPerferIndex(index+1);//index ++
				else if(user1.getPromoted()==0){//第二次机会！
					user1.setPromoted(1);
					user1.setPerferIndex(0);
				}
				
				if(user2.getFlag()==0){//约会
					if(user2.getPerfer().contains(user1)){//如果b的perfer中有a，即b不拒绝a
						//set a and a dating 
						map.put(user1, user2);map.put(user2, user1);
						//update user1\2's status;
						user1.setFlag(1);user2.setFlag(1);
						System.out.println(user1.getName()+"---"+user2.getName());
					}
				}
				else{//b有约会对象
					User user3 = map.get(user2);//获取b的dating对象 
					List<User> per2 = user2.getPerfer();
					int a = Integer.MAX_VALUE;
					if(per2.contains(user1))
						a = per2.indexOf(user1);//b对a的好感顺位,a为MAX时即表示拒绝
					int c = per2.indexOf(user3);//b对当前的对象好感顺位
					
					//根绝新的选择规则选择喜欢的
					if(newRule1(user1,user2,user3)
							||newRule2(user1,user2,user3,a,c)){//满足两条件任一,选user1
						//update user3's status and remove
						user3.setFlag(0);
						map.remove(user2);map.remove(user3);
						//set a and a dating 
						map.put(user1, user2);map.put(user2, user1);
						//update user1\2's status;
						user1.setFlag(1);user2.setFlag(1);
						System.out.println(user1.getName()+"---"+user2.getName());
					}

				}	
				//对于girls来说，如果是认识的人user1告白了，那么所有认识的人中排在user1好感顺位后面的直接gg
				List<User> acq2 = user2.getAcquaintance();
				if(acq2.contains(user1)){
					for(User u:acq2){
						if(check(user1,u,user2.getPerfer())){//更喜欢user1的话，排在后面的人gg吧！
							acq2.remove(u);
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
	
	private boolean check(User user1, User u, List<User> per2) {
		int a = Integer.MAX_VALUE;
		int c = Integer.MAX_VALUE;
		if(per2.contains(user1))
			a = per2.indexOf(user1);//b对a的好感顺位,a为MAX时即表示拒绝
		if(per2.contains(u))
			c = per2.indexOf(u);//b对c的好感顺位...
		if(a<c)//如果更喜欢user1
			return true;
		return false;
	}

	//Def6.1
	private boolean newRule1(User user1, User user2, User user3) {
		List<User> acq2 = user2.getAcquaintance();
		if(!acq2.contains(user1)&&!acq2.contains(user3)
				&&user1.getPromoted()==1&&user3.getPromoted()==0)//都不认识且user1 Promoted(人家告白过了)选他！(Up>U A>U)
			return true;
		else if(acq2.contains(user1)&&!acq2.contains(user3)//认识user1，选他！
				&&user3.getPromoted()==0)
			return true;
		return false;		
	}
	
	//Def6.2
	private boolean newRule2(User user1, User user2, User user3,int a, int c) {
		if(!newRule1(user1, user2, user3)&&!newRule1(user3, user2, user1)//喜欢user1,选他！(这里都是U-U Up-A A-A Up-Up的对等关系)
				&&a<c)
			return true;
		return false;		
	}


	private boolean ExistFree(List<User>A){
		for(int i=0;i<A.size();i++){
			if(A.get(i).getFlag()==0&&A.get(i).getPerferIndex()<A.get(i).getPerfer().size())
				return true;
		}
		return false;		
	}
}
