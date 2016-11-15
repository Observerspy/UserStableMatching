package Obe.Cluster.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Stack;

import org.hibernate.SessionFactory;

import Obe.Cluster.Dao.CountCossim;
import Obe.Cluster.Dao.Compare.MapCompare;
import Obe.Cluster.Dto.User;
import Obe.Db.Dao.DBdao;
import Obe.Db.Dto.Book;
import Obe.Db.Dto.Movie;
import Obe.Db.Dto.Userbook;
import Obe.Db.Dto.Usermovie;
import Obe.Util.DbInitial;
import Obe.Util.file;

public class DB2SVM {
	private static int NUM = 62558;
	private static int C = 1;//under sample rate
	private static HashMap<String,Integer> wordsMap = new HashMap<String,Integer>();
	private static List<Map.Entry<String,Integer>> words = new ArrayList<Map.Entry<String,Integer>>();
	private static String resultpath = "D:/Work/UserStableMatching/etc/SVM/result/";

	private static String filepath = "D:/Work/UserStableMatching/etc/SVM/";

	
	public static void main(String[] args) throws IOException {
		SessionFactory sf = DbInitial.getNewDb().getFactory();
    	DBdao db = new DBdao();
    	List<Obe.Db.Dto.User> u = db.getPart(sf, "getUser",NUM);//取100条
    	
   		List<User> A = new ArrayList<User>();//from DB -->ub
		List<User> B = new ArrayList<User>();//from DB -->um
		
		List<Double> cos = new ArrayList<Double>();
		int sum = 0;int count = 0;
    	for(Obe.Db.Dto.User id:u){
    		//System.out.println(id.getUnionId()+"==================book===========================");
    		List<Object> ub =  db.getId(sf, "getBookUser", id.getUnionId());
    		List<Map.Entry<String,Integer>> result = new ArrayList<Map.Entry<String,Integer>>();
    		HashMap<String,Integer> words = new HashMap<String,Integer>();
    		if(ub.size()>0){
    			User userbook = new User();
        		userbook.setName(id.getUnionId()+"--book");
        		String text = "";
        		List<String> list = new ArrayList<String>();//记录每一条数据标签
        		for(Object o :ub){
        			Userbook ubo = (Userbook)o;
            		List<Object> book =  db.getId(sf, "getBook", ubo.getId().getBid());

            		Book booko = new Book();
            		if(book.size()>0&&book.get(0)!=null)
            			booko = (Book)book.get(0);
            		list.add(booko.getTags());
            		text += booko.getTags();
            		//System.out.println(booko.getName()+" -- "+booko.getTags()+" -- "+ubo.getTags()+" -- "+ubo.getComment());
        		}
        		userbook.setList(list); 
        		countotalMap(text);
        		words=countWord(text,words);
        		result.addAll(words.entrySet());
        		userbook.setWordsCount(result);
        		//ouputWordsCount(result);
        		//userbook.setText(text);
        		A.add(userbook);
    		}
    		
    		//System.out.println("=====================movie========================");
    		List<Object> um =  db.getId(sf, "getMovieUser", id.getUnionId());
    		List<Map.Entry<String,Integer>> result1 = new ArrayList<Map.Entry<String,Integer>>();
    		HashMap<String,Integer> words1 = new HashMap<String,Integer>();
    		if(um.size()>0){
    			User usermovie = new User();
        		usermovie.setName(id.getUnionId()+"--movie");
        		List<String> list = new ArrayList<String>();//记录每一条数据标签
        		String text1 = "";
        		for(Object o :um){
        			Usermovie umo = (Usermovie)o;
            		List<Object> movie =  db.getId(sf, "getMovie", umo.getId().getMid());
            		
            		Movie movieo = new Movie();
            		if(movie.size()>0&&movie.get(0)!=null)
            			movieo = (Movie)movie.get(0);
            		list.add(movieo.getTags());
            		text1 += movieo.getTags();
            		//System.out.println(movieo.getName()+" -- "+movieo.getTags()+" -- "+umo.getTags()+" -- "+umo.getComment());
        		}
        		
        		usermovie.setList(list);  		
        		countotalMap(text1);
        		words1=countWord(text1,words1);
        		result1.addAll(words1.entrySet());
        		usermovie.setWordsCount(result1);
        		//ouputWordsCount(result1);
        		//usermovie.setText(text1);
        		B.add(usermovie);
    		}
    		
    	}
    	
    	words.addAll(wordsMap.entrySet());
//		for(Iterator<Map.Entry<String,Integer>> it=words.iterator();it.hasNext();){
//			Map.Entry entry = (Map.Entry) it.next();
//			String word = (String) entry.getKey();
//			Integer index = (Integer) entry.getValue();
//			if(index>0)//取词频大于1的
//				flag++;
//		}
//    	System.out.println(flag);
    	
    	//输出训练集与测试集-->pairwise==>计算相似度
    	file f = new file();
		CountCossim cossim = new CountCossim();
    	f.setoutpath(filepath+"train/lifespec-under"+C+"_"+NUM);f.delete();
    	//output under sample
    	file f1 = new file();
    	f1.setoutpath(resultpath+"lifespec-under"+C+"_"+NUM+"_p");f1.delete();

    	for(User a:A){
    		
    		Stack<Integer> ran = new Stack<Integer>();//随机C个负例位置放在栈里
    		for(int k=0;k<C;k++){
        		long t = System.currentTimeMillis();//获得当前时间的毫秒数
        		Random rd = new Random(t);//作为种子数传入到Random的构造器中
        		int id = rd.nextInt(B.size());
        		if(ran.contains(id)){
        			k--;
        			continue;
        		}
        		//System.out.println(id);//生成随即整数
        		
        		ran.push(id);
    		}
    		
    		//正例
    		for(User b:B){    				
        		if(a.getName().split("--")[0].equals(b.getName().split("--")[0])){//正例
        			double tagsCos = cossim.getCos(a.getWordsCount(), b.getWordsCount());
        		    	
        			int tagsCom = getCommon(a.getWordsCount(),b.getWordsCount());
        				
        			f.write("+1"+" 1:"+tagsCos+" 2:"+tagsCom+"\r\n");
        			f1.write(a.getName().split("--")[0]+"---"+b.getName().split("--")[0]+"\r\n");
        		}
    		}
    		//负例
    		for(int j=0;j<C;j++){
    			int id = ran.pop();
    			User b = B.get(id);
    			
				double tagsCos = cossim.getCos(a.getWordsCount(), b.getWordsCount());
		    	
				int tagsCom = getCommon(a.getWordsCount(),b.getWordsCount());
				
				f.write("-1"+" 1:"+tagsCos+" 2:"+tagsCom+"\r\n");
    			f1.write(a.getName().split("--")[0]+"---"+b.getName().split("--")[0]+"\r\n");

    		}
    		
    	}
//    	for(User um:B){
//        	//训练
//    		//正例
//    		int id = Integer.valueOf(um.getName().split("--")[0]);
//    		f.setoutpath(filepath+"train/"+id);f.delete();
//    		List<String> list = um.getList();
//    		for(String tags:list){
//    			if(tags!=null&&tags!=""){
//            		HashMap<String,Integer> result = new HashMap<String,Integer>();
//        			result=countWord(tags,result);
//        			String s = ouputWordsCount(result);
//        			if(!s.equals("\r\n"))
//                		f.write("+1 "+s);
//    			}
//    		}
//    		//全负例
//    		for(User ume:B){
//    			int idx = Integer.valueOf(ume.getName().split("--")[0]);
//    			if(id!=idx){
//    				List<String> list1 = ume.getList();
//    	    		for(String tags:list1){
//    	    			if(tags!=null&&tags!=""){
//    	        			HashMap<String,Integer> result = new HashMap<String,Integer>();
//    	    				result=countWord(tags,result);
//    	        			String s = ouputWordsCount(result);
//    	        			if(!s.equals("\r\n"))
//    	                		f.write("-1 "+s);
//    	        		}
//    	    		}
//    			}
//    		}

////    		//测试
//    		f.setoutpath(filepath+"test/"+id);f.delete();
//    		for(User ub:A){
//    			int idx = Integer.valueOf(ub.getName().split("--")[0]);
//    			//测试负例
//    			if(id!=idx){
//    	    		List<String> list1 = ub.getList();
//    	    		for(String tags:list1){
//    	    			if(tags!=null&&tags!=""){
//    	            		HashMap<String,Integer> result = new HashMap<String,Integer>();
//    	        			result=countWord(tags,result);
//    	        			String s = ouputWordsCount(result);
//    	        			if(!s.equals("\r\n"))
//    	                		f.write("-1 "+s);
//    	    			}
//    	    		}
//    			}
//    			//测试正例
//    			else if(id==idx){
//    	    		List<String> list1 = ub.getList();
//    	    		for(String tags:list1){
//    	    			if(tags!=null&&tags!=""){
//    	            		HashMap<String,Integer> result = new HashMap<String,Integer>();
//    	        			result=countWord(tags,result);
//    	        			String s = ouputWordsCount(result);
//    	        			if(!s.equals("\r\n"))
//    	                		f.write("+1 "+s);
//    	    			}
//    	    		}
//    			}
//    		}
    		
    		int m;
    		m=0;
    	
    	
    	
	}
	
	private static void countotalMap(String text) {
		text = text.replaceAll("\\d+", " ");
		String[] words = text.split(" ");
		for(String word:words){
			Integer counter = wordsMap.get(word);
			if (counter == null) 
				counter = 1;
		    else 
				counter++; 
			wordsMap.put(word, counter);
//			wordsMap.put(word, flag);
//			flag++;
		}
	}
	
	private static HashMap<String, Integer> countWord(String text, HashMap<String, Integer> result) {
		text = text.replaceAll("\\d+", " ");
		String[] words = text.split(" ");
		for(String word:words){
			Integer counter = result.get(word);
			if (counter == null) 
				counter = 1;
		    else 
				counter++; 
			result.put(word, counter);
		}
		
		return result;
	}
	
	private static String ouputWordsCount(HashMap<String, Integer> result){
		String str = "";int i = 1;String txt = "";
		for(Iterator<Map.Entry<String,Integer>> it=words.iterator();it.hasNext();){
			Map.Entry entry = (Map.Entry) it.next();
			String word = (String) entry.getKey();
			Integer index = (Integer) entry.getValue();
			if(index>0)//取词频大于1的
				if(result.get(word) != null){
					Integer val = (Integer) result.get(word);
					str += i+":"+val+".0 ";
					i++;
					txt += word+" ";
				}

		}
		System.out.println(txt);
		str += "\r\n";
		return str;
	}
	
	private static int getCommon(List<Entry<String, Integer>> tags,
			List<Entry<String, Integer>> tags2) {
		int common = 0;
		HashMap<String,Integer> result = new HashMap<String,Integer>();
		for(Iterator<Map.Entry<String,Integer>> it=tags.iterator();it.hasNext();){
			Map.Entry entry = (Map.Entry) it.next();
			String app = (String) entry.getKey();			
			Integer val = (Integer) entry.getValue();
			result.put(app, val);
		}	
		for(Iterator<Map.Entry<String,Integer>> it=tags2.iterator();it.hasNext();){
			Map.Entry entry = (Map.Entry) it.next();
			String app = (String) entry.getKey();			
			if(result.containsKey(app))
				common++;
		}	
		return common;
	}
}
