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
import Obe.Cluster.Dao.StableMatchEX;
import Obe.Cluster.Dao.Compare.MapCompare;
import Obe.Cluster.Dto.Pair;
import Obe.Cluster.Dto.User;
import Obe.Db.Dao.DBdao;
import Obe.Db.Dto.Information;
import Obe.Db.Dto.Tags;
import Obe.Util.DbInitial;
import Obe.Util.LoadBigFile;
import Obe.Util.file;

public class SMMain_P {
	private static int NUM = 4;
	private static int flag = 0;
	private static HashMap<String,Integer> wordsMap = new HashMap<String,Integer>();
	private static List<Map.Entry<String,Integer>> words = new ArrayList<Map.Entry<String,Integer>>();
	private static String filepath = "F:/work/UserStableMatching/etc/SVM/";

	public static void main(String[] args) throws Exception {
		SessionFactory sf = DbInitial.getNewDb().getFactory();
    	DBdao db = new DBdao();
    	List<Obe.Db.Dto.People> u = db.getPart_p(sf, "getPeople",NUM);//取100条
    	
   		List<User> A = new ArrayList<User>();//from DB -->ub
		List<User> B = new ArrayList<User>();//from DB -->um
		//List<User> C = new ArrayList<User>();//from DB -->um
		
		List<Double> cos = new ArrayList<Double>();
		Double sum = 0.0;int count = 0;
    	for(Obe.Db.Dto.People id:u){
    		List<Object> ui =  db.getId(sf, "getInfor", Integer.valueOf(id.getId()));
    		
    		for(Object o:ui){
    			Information user = (Information)o;
    			String str = user.getId().getSource();
    			if(str.equals("programmer")){
    				function(A,user);
    			}
    			else if(str.equals("stackoverflow")){
    				function(B,user);
    			}
//    			else if(str.equals("superuser")){
//    				function(C,user,db,sf);
//    			}    		
    		}
    	}
    	
    	//add perfer list	
		LoadBigFile load = new LoadBigFile();
		load.setPath(filepath+"result/alltrain/out_r.txt");
		String text = load.Load();
		String[] texts = text.split("\n");
		// for A
		for(int i=0;i<NUM;i++){
			List<Double> l = new ArrayList<Double>();
			HashMap<Double,Integer> map = new HashMap<Double,Integer>();
			for(int j=0;j<NUM;j++){
				String s = texts[NUM*i+j+1].split(" ")[1];
				l.add(Double.valueOf(s));
				map.put(Double.valueOf(s),j);
			}
			Collections.sort(l);
			Collections.reverse(l);
			for(int j=0;j<NUM;j++){
				int id = map.get(l.get(j));
				A.get(i).getPerfer().add(B.get(id));
			}		
		}
		//for B
		for(int i=0;i<NUM;i++){
			List<Double> l = new ArrayList<Double>();
			HashMap<Double,Integer> map = new HashMap<Double,Integer>();
			for(int j=0;j<NUM;j++){
				String s = texts[NUM*j+i+1].split(" ")[1];
				l.add(Double.valueOf(s));
				map.put(Double.valueOf(s),j);
			}
			Collections.sort(l);
			Collections.reverse(l);
			for(int j=0;j<NUM;j++){
				int id = map.get(l.get(j));
				B.get(i).getPerfer().add(A.get(id));
			}		
		}
		
		//do SM
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
		int correct = 0;
		System.out.println("==========RESULT:============");
		Iterator<Entry<String, Pair>> iter1 = show.entrySet().iterator();
		while (iter1.hasNext()) {
			Map.Entry entry = (Map.Entry) iter1.next();
			String name = (String) entry.getKey();
			Pair p = (Pair) entry.getValue();
			String u1 = name.split("---")[0].split("--")[0];
			String u2 = name.split("---")[1].split("--")[0];
			if(u1.equals(u2))
				correct++;
			System.out.println(name);
		}
		System.out.println("ACCUARCY: "+(double)correct/NUM);
	}
	
	private static void function(List<User> A, Information user) throws Exception {
		User u = new User();    				
		u.setName(user.getId().getId()+"--"+user.getId().getSource());
		A.add(u);		
	}
}
