package Obe.Cluster.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.SessionFactory;

import Obe.Cluster.Dao.StableMatch;
import Obe.Cluster.Dao.Compare.MapCompare;
import Obe.Cluster.Dao.Compare.MapCompareUserProb;
import Obe.Cluster.Dto.Pair;
import Obe.Cluster.Dto.User;
import Obe.Cluster.Dto.UserProb;
import Obe.Db.Dao.DBdao;
import Obe.Db.Dto.Book;
import Obe.Db.Dto.Movie;
import Obe.Db.Dto.Userbook;
import Obe.Db.Dto.Usermovie;
import Obe.Util.DbInitial;
import Obe.Util.LoadBigFile;

public class SMMain {
	private static int NUM = 62558;
	private static String str = "lifespec-under1_62558";
	//private static HashMap<String,Integer> wordsMap = new HashMap<String,Integer>();
	//private static List<Map.Entry<String,Integer>> words = new ArrayList<Map.Entry<String,Integer>>();

	private static String filepath = "D:/Work/UserStableMatching/etc/SVM/";
	
	public static void main(String[] args) throws Exception {
		SessionFactory sf = DbInitial.getNewDb().getFactory();
    	DBdao db = new DBdao();
    	List<Obe.Db.Dto.User> u = db.getPart(sf, "getUser",NUM);//取100条
    	
   		List<User> A = new ArrayList<User>();//from DB -->ub
		List<User> B = new ArrayList<User>();//from DB -->um
		//HashMap<Integer,User> Amap = new HashMap<Integer,User>();
		//HashMap<Integer,User> Bmap = new HashMap<Integer,User>();

    	for(Obe.Db.Dto.User id:u){
    		//System.out.println(id.getUnionId()+"==================book===========================");
    		List<Object> ub =  db.getId(sf, "getBookUser", id.getUnionId());
    		HashMap<String,Integer> words = new HashMap<String,Integer>();
    		if(ub.size()>0){
    			User userbook = new User();
        		userbook.setName(id.getUnionId()+"--book");
        		A.add(userbook);
        		//Amap.put(id.getUnionId(), userbook);
    		}
    		
    		//System.out.println("=====================movie========================");
    		List<Object> um =  db.getId(sf, "getMovieUser", id.getUnionId());
    		if(um.size()>0){
    			User usermovie = new User();
        		usermovie.setName(id.getUnionId()+"--movie");
        		B.add(usermovie);
        		//Bmap.put(id.getUnionId(), usermovie);
    		}	
    	}
    	
    	//add perfer list	
		LoadBigFile load = new LoadBigFile();
		load.setPath(filepath+"result/"+str+"/out_r.txt");
		String text = load.Load();
		String[] texts = text.split("\n");
		
		//欠采样
		//List<User> Ap = new ArrayList<User>();
		//List<User> Bp = new ArrayList<User>();
		
		HashMap<String,Integer> m = readUnderP();
		Iterator<Entry<String,Integer>> iterx = m.entrySet().iterator();
		while (iterx.hasNext()) {
			Map.Entry entry = (Map.Entry) iterx.next();
			String pair = (String) entry.getKey();
			Integer pos = (Integer) entry.getValue();
			
			String a = pair.split("---")[0];UserProb upa = new UserProb();
			String b = pair.split("---")[1];UserProb upb = new UserProb();

			User ua = find(A,a);//Amap.get(Integer.valueOf(a));
			User ub = find(B,b);//Bmap.get(Integer.valueOf(b));

			upa.setU(ub);upa.setP(Double.valueOf(texts[pos].split(" ")[1]));
			ua.getUp().add(upa);
			//Ap.add(ua);
			upb.setU(ua);upb.setP(Double.valueOf(texts[pos].split(" ")[1]));
			ub.getUp().add(upb);
			//Bp.add(ub);
		
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
//全采样		
//		// for A
//		for(int i=0;i<A.size();i++){
//			List<Double> l = new ArrayList<Double>();
//			HashMap<Double,Integer> map = new HashMap<Double,Integer>();
//			for(int j=0;j<B.size();j++){
//				String s = texts[A.size()*i+j+1].split(" ")[1];
//				l.add(Double.valueOf(s));
//				map.put(Double.valueOf(s),j);
//			}
//			Collections.sort(l);
//			Collections.reverse(l);
//			for(int j=0;j<B.size();j++){
//				int id = map.get(l.get(j));
//				A.get(i).getPerfer().add(B.get(id));
//			}		
//		}
//		//for B
//		for(int i=0;i<B.size();i++){
//			List<Double> l = new ArrayList<Double>();
//			HashMap<Double,Integer> map = new HashMap<Double,Integer>();
//			for(int j=0;j<A.size();j++){
//				String s = texts[B.size()*j+i+1].split(" ")[1];
//				l.add(Double.valueOf(s));
//				map.put(Double.valueOf(s),j);
//			}
//			Collections.sort(l);
//			Collections.reverse(l);
//			for(int j=0;j<A.size();j++){
//				int id = map.get(l.get(j));
//				B.get(i).getPerfer().add(A.get(id));
//			}		
//		}
		
//		//faker
//		User faker = new User();
//		faker.setName(">>FAKER<<");
//		int key = 0;
//		if(A.size()<B.size()){
//			int l = A.size();
//			for(int i=0;i<B.size()-l;i++){
//				A.add(faker);//let A as boys
//			}
//		}
//		else if(A.size()>B.size()){
//			key = 1;
//			for(int i=0;i<A.size()-B.size();i++){
//				B.add(faker);//let B as boys
//			}
//		}		
		//do SM
		StableMatch sm = new StableMatch();
		HashMap<User,User> map = new HashMap<>();
		//if(key==0)
			map = sm.StartMatching(A);
		//else
			//map = sm.StartMatching(B);		
		
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
			if(!u1.equals(u2)){
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
