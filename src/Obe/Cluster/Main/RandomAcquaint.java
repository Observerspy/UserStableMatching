package Obe.Cluster.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import org.hibernate.SessionFactory;

import Obe.Cluster.Dto.User;
import Obe.Db.Dao.DBdao;
import Obe.Util.DbInitial;

public class RandomAcquaint {
	private static int NUM = 100;
	private static int Ran = 10;

	public static void main(String[] args) {
		SessionFactory sf = DbInitial.getNewDb().getFactory();
    	DBdao db = new DBdao();
    	List<Obe.Db.Dto.User> u = db.getPart(sf, "getUser",NUM);//取100条
    	
   		List<User> A = new ArrayList<User>();//from DB -->ub
		List<User> B = new ArrayList<User>();//from DB -->um

    	for(Obe.Db.Dto.User id:u){
    		//System.out.println(id.getUnionId()+"==================book===========================");
    		List<Object> ub =  db.getId(sf, "getBookUser", id.getUnionId());
    		HashMap<String,Integer> words = new HashMap<String,Integer>();
    		if(ub.size()>0){
    			User userbook = new User();
        		userbook.setName(id.getUnionId()+"--book");
        		A.add(userbook);
    		}
    		
    		//System.out.println("=====================movie========================");
    		List<Object> um =  db.getId(sf, "getMovieUser", id.getUnionId());
    		if(um.size()>0){
    			User usermovie = new User();
        		usermovie.setName(id.getUnionId()+"--movie");
        		B.add(usermovie);
    		}	
    	}
    	
		Stack<Integer> ran = new Stack<Integer>();//随机Ran个Acquaint位置放在栈里
		for(int k=0;k<Ran;k++){
    		long t = System.currentTimeMillis();//获得当前时间的毫秒数
    		Random rd = new Random(t);//作为种子数传入到Random的构造器中
    		int id = rd.nextInt(A.size());
    		if(ran.contains(id)){
    			k--;
    			continue;
    		}    		
    		ran.push(id);
		}
		
		for(int i=0;i<Ran;i++){
			int id = ran.pop();
			User a = A.get(id);
			User b = find(B,a.getName().split("--")[0]);
			if(b!=null){
				a.getAcquaintance().add(b);
				//System.out.println(a.getName()+"---"+b.getName());
			}
		}
		
		int m;
		m=0;
	}
	
	private static User find(List<User> A, String a2) {
		for(User a:A){
			if(a.getName().split("--")[0].equals(a2))
				return a;
		}
		return null;
	}
}
