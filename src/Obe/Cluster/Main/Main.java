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

import Obe.Cluster.Dao.CountCossim;
import Obe.Cluster.Dao.StableMatch;
import Obe.Cluster.Dao.Compare.MapCompare;
import Obe.Cluster.Dao.Compare.MapCompareDouble;
import Obe.Cluster.Dto.Pair;
import Obe.Cluster.Dto.User;
import Obe.Db.Dao.DBdao;
import Obe.Db.Dto.Book;
import Obe.Db.Dto.Movie;
import Obe.Db.Dto.Userbook;
import Obe.Db.Dto.Usermovie;
import Obe.Util.DbInitial;
import Obe.Util.file;

public class Main {
	private static int NUM = 100;

	public static void main(String[] args) throws IOException {
		SessionFactory sf = DbInitial.getNewDb().getFactory();
    	DBdao db = new DBdao();
    	List<Obe.Db.Dto.User> u = db.getPart(sf, "getUser",NUM);//取100条
    	
   		List<User> A = new ArrayList<User>();//from DB -->ub
		List<User> B = new ArrayList<User>();//from DB -->um
		
//		List<Double> cos = new ArrayList<Double>();
//		Double sum = 0.0;int count = 0;
//    	for(Obe.Db.Dto.User id:u){
//    		//System.out.println(id.getUnionId()+"==================book===========================");
//    		List<Object> ub =  db.getId(sf, "getBookUser", id.getUnionId());
//    		List<Map.Entry<String,Integer>> result = new ArrayList<Map.Entry<String,Integer>>();
//    		if(ub.size()>0){
//    			User userbook = new User();
//        		userbook.setName(id.getUnionId()+"--book");
//        		String text = "";
//        		for(Object o :ub){
//        			Userbook ubo = (Userbook)o;
//            		List<Object> book =  db.getId(sf, "getBook", ubo.getId().getBid());
//
//            		Book booko = new Book();
//            		if(book.size()>0&&book.get(0)!=null)
//            			booko = (Book)book.get(0);
//            		text += booko.getTags();
//            		//System.out.println(booko.getName()+" -- "+booko.getTags()+" -- "+ubo.getTags()+" -- "+ubo.getComment());
//        		}
//        		    		
//        		result=countWord(text,result);
//        		userbook.setWordsCount(result);
//        		ouputWordsCount(result);
//        		userbook.setText(text);
//        		A.add(userbook);
//    		}
//    		
//    		//System.out.println("=====================movie========================");
//    		List<Object> um =  db.getId(sf, "getMovieUser", id.getUnionId());
//    		List<Map.Entry<String,Integer>> result1 = new ArrayList<Map.Entry<String,Integer>>();
//    		if(um.size()>0){
//    			User usermovie = new User();
//        		usermovie.setName(id.getUnionId()+"--movie");
//        		String text1 = "";
//        		for(Object o :um){
//        			Usermovie umo = (Usermovie)o;
//            		List<Object> movie =  db.getId(sf, "getMovie", umo.getId().getMid());
//            		
//            		Movie movieo = new Movie();
//            		if(movie.size()>0&&movie.get(0)!=null)
//            			movieo = (Movie)movie.get(0);
//            		text1 += movieo.getTags();
//            		//System.out.println(movieo.getName()+" -- "+movieo.getTags()+" -- "+umo.getTags()+" -- "+umo.getComment());
//        		}
//        		
//        		result1=countWord(text1,result1);
//        		usermovie.setWordsCount(result1);
//        		ouputWordsCount(result1);
//        		usermovie.setText(text1);
//        		B.add(usermovie);
//    		}
//    		
//    		//System.out.println("====================cossine=========================");
//    		
////    		//以下用于计算同一用户不同账号的相似度
////			double val = -Integer.MAX_VALUE;
////    		CountCossim cossim = new CountCossim();
////			val = cossim.getCos(result, result1);
////    		//System.out.println(id.getUnionId()+" val: "+val);
////    		cos.add(val);
////    		
////    		if(!Double.isNaN(val)){
////    			sum += val;
////    			count++;
////    		}
//    		
//    		int m;
//    		m=0;
//    	}
    	
//    	//以下输出同一用户不同账号平均相似度和8分为相似度
//		System.out.print("sum: "+sum+"sum/count: "+sum/count+" ");
//		int X = 0;
//		for(Double v:cos){
//			if(v>(sum/count))
//				X++;
//		}
//		System.out.print("X: "+X+" ");
//		Collections.sort(cos);
//		Collections.reverse(cos);
//		System.out.println("80%: "+cos.get((int)Math.floor(NUM*0.8)));
		
		//根据信息计算perfer值并排序
//		file f = new file();
//		System.out.println("counting cosine similarity...");
//		f.setoutpath("F:/work/UserStableMatching/etc/cossim_"+NUM+".txt");f.delete();
//		f.write("cosine similarity:\r\n");
//		for(User a:A){
//			List<Map.Entry<String,Double>> result = new ArrayList<Map.Entry<String,Double>>(); 
//			HashMap<String,Double> map = new HashMap<String, Double>();
//			
//			for(User b:B){
//				double val = -Double.MAX_VALUE;
//				CountCossim cossim = new CountCossim();
//				val = cossim.getCos(a.getWordsCount(), b.getWordsCount());
//				
//				map.put(a.getName()+" -AND- "+b.getName(), val);
//
//			}
//			
//			result.addAll(map.entrySet());
//			MapCompareDouble mc = new MapCompareDouble();
//			Collections.sort(result,mc);
//			
//			for(Iterator<Map.Entry<String,Double>> it=result.iterator();it.hasNext();){
//				Map.Entry entry = (Map.Entry) it.next();
//				String app = (String) entry.getKey();			
//				Double val = (Double) entry.getValue();
//				//System.out.println(app+"="+val);
//				f.write(app+" sim:"+val+"\r\n");
//			}
//			
//		}
		//将perfer值分别填入各自的perferlist中，并只保留perfer>K的对象
    	
		//用户人数不等，向少的一方添加虚假用户，参与计算但不输出结果???有必要么???
		User faker = new User();
		faker.setName(">>FAKER<<");
		int key = 0;
		if(A.size()<B.size()){
			for(int i=0;i<B.size()-A.size();i++){
				A.add(faker);//let A as boys
			}
		}
		else if(A.size()>B.size()){
			key = 1;
			for(int i=0;i<A.size()-B.size();i++){
				B.add(faker);//let B as boys
			}
		}
		
		//do stable matching
		StableMatch sm = new StableMatch();
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

	private static void ouputWordsCount(List<Entry<String, Integer>> result) {
		for(Iterator<Map.Entry<String,Integer>> it=result.iterator();it.hasNext();){
			Map.Entry entry = (Map.Entry) it.next();
			String app = (String) entry.getKey();			
			Integer val = (Integer) entry.getValue();
			System.out.print(app+":="+val+" ");
		}		
		System.out.println("");
	}

	private static List<Entry<String, Integer>> countWord(String text, List<Entry<String, Integer>> result) {
		text = text.replaceAll("\\d+", " ");
		String[] words = text.split(" ");
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		for(String word:words){
			Integer counter = map.get(word);
			if (counter == null) 
				counter = 1;
		    else 
				counter++; 
			map.put(word, counter);
		}
		
		result.addAll(map.entrySet());
		MapCompare mc = new MapCompare();   
		Collections.sort(result,mc);
		
		return result;
	}

}
