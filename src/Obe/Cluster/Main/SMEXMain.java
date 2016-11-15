package Obe.Cluster.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.Map.Entry;

import org.hibernate.SessionFactory;

import Obe.Cluster.Dao.StableMatchEX;
import Obe.Cluster.Dao.Compare.MapCompareUserProb;
import Obe.Cluster.Dto.Pair;
import Obe.Cluster.Dto.User;
import Obe.Cluster.Dto.UserProb;
import Obe.Db.Dao.DBdao;
import Obe.Util.DbInitial;
import Obe.Util.LoadBigFile;

public class SMEXMain {
	private static int NUM = 62558;
	//private static int Ran = 10;
	private static String str = "lifespec-under1_62558";

	private static String filepath = "D:/Work/UserStableMatching/etc/SVM/";
	
	public static void main(String[] args) throws Exception {
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
    	
    	//add perfer list	
		LoadBigFile load = new LoadBigFile();
		load.setPath(filepath+"result/"+str+"/out_r.txt");
		String text = load.Load();
		String[] texts = text.split("\n");
		
		HashMap<String,Integer> m = readUnderP();
		Iterator<Entry<String,Integer>> iterx = m.entrySet().iterator();
		while (iterx.hasNext()) {
			Map.Entry entry = (Map.Entry) iterx.next();
			String pair = (String) entry.getKey();
			Integer pos = (Integer) entry.getValue();
			
			String a = pair.split("---")[0];UserProb upa = new UserProb();
			String b = pair.split("---")[1];UserProb upb = new UserProb();

			User ua = find(A,a);
			User ub = find(B,b);

			upa.setU(ub);upa.setP(Double.valueOf(texts[pos].split(" ")[1]));
			ua.getUp().add(upa);
			upb.setU(ua);upb.setP(Double.valueOf(texts[pos].split(" ")[1]));
			ub.getUp().add(upb);
		
		}
		
		for(User ua:A){
			List<UserProb> l = ua.getUp();
			MapCompareUserProb mc = new MapCompareUserProb();
			Collections.sort(l,mc);
			
			for(UserProb up:l){
				ua.getPerfer().add(up.getU());
			}
		}
		for(User ub:B){
			List<UserProb> l = ub.getUp();
			MapCompareUserProb mc = new MapCompareUserProb();
			Collections.sort(l,mc);
			
			for(UserProb up:l){
				ub.getPerfer().add(up.getU());
			}
		}
		
		//acq list
//		Stack<Integer> ran = new Stack<Integer>();//随机Ran个Acquaint位置放在栈里
//		for(int k=0;k<Ran;k++){
//    		long t = System.currentTimeMillis();//获得当前时间的毫秒数
//    		Random rd = new Random(t);//作为种子数传入到Random的构造器中
//    		int id = rd.nextInt(A.size());
//    		if(ran.contains(id)){
//    			k--;
//    			continue;
//    		}    		
//    		ran.push(id);
//		}
//		
//		for(int i=0;i<Ran;i++){
//			int id = ran.pop();
//			User a = A.get(id);
//			User b = find(B,a.getName().split("--")[0]);
//			if(b!=null){
//				a.getAcquaintance().add(b);
//				//System.out.println(a.getName()+"---"+b.getName());
//			}
//		}
		
		int[] acq = {50893,168020,217653,46667,939,165079,90257,2962,55484,295243,59911,117434,238706,147923,176840,28816,235366,98506,244298,128065,214553,155846,216593,261310,126043,25089,172953,223868,9774,206707,133336,994,78303,259972,119686,504,112879,204612,189369,75124,161544,55743,55740,17686,163105,227536,98641,202474,189346,92791,254602,297071,95718,197840,154759,215118,278530,144919,167347,14713,237631,125443,200228,263586,226857,274094,268314,256918,265116,11644,276704,165821,265121};
		for(int i=0;i<acq.length;i++){
			User b = find(B,String.valueOf(acq[i]));
			User a = find(A,b.getName().split("--")[0]);
			if(a!=null){
				b.getAcquaintance().add(a);
				//System.out.println(a.getName()+"---"+b.getName());
			}
		}
		System.out.println("acq size:"+acq.length);
		//faker
		User faker = new User();
		faker.setName(">>FAKER<<");
		int key = 0;
		if(A.size()<B.size()){
			int l = A.size();
			for(int i=0;i<B.size()-l;i++){
				A.add(faker);//let A as boys
			}
		}
		else if(A.size()>B.size()){
			key = 1;
			for(int i=0;i<A.size()-B.size();i++){
				B.add(faker);//let B as boys
			}
		}		
		//do SM
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
		int correct = 0;
		System.out.println("==========RESULT:============");
		Iterator<Entry<String, Pair>> iter1 = show.entrySet().iterator();
		while (iter1.hasNext()) {
			Map.Entry entry = (Map.Entry) iter1.next();
			String name = (String) entry.getKey();
			Pair p = (Pair) entry.getValue();
			String u1 = name.split("---")[0].split("--")[0];
			String u2 = name.split("---")[1].split("--")[0];
			if(u1.equals(u2)){
				correct++;
				System.out.println(name);
			}
		}
		System.out.println("+1: "+correct+" ACCUARCY: "+(double)correct/show.size());
	}

	private static User find(List<User> A, String a2) {
		for(User a:A){
			if(a.getName().split("--")[0].equals(a2))
				return a;
		}
		return null;
	}

	private static HashMap<String, Integer> readUnderP() throws Exception {
		HashMap<String, Integer> m = new HashMap<String, Integer>();
		LoadBigFile load = new LoadBigFile();
		load.setPath(filepath+"result/"+str+"_p");
		String text = load.Load();
		String[] texts = text.split("\r\n");
		
		for(int i=0;i<texts.length;i++){
			m.put(texts[i], i+1);
		}
		return m;
	}
}
