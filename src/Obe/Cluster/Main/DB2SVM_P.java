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

import org.hibernate.SessionFactory;

import Obe.Cluster.Dao.CountCossim;
import Obe.Cluster.Dao.EditDistance;
import Obe.Cluster.Dao.StableMatch;
import Obe.Cluster.Dao.Compare.MapCompare;
import Obe.Cluster.Dto.Pair;
import Obe.Cluster.Dto.User;
import Obe.Db.Dao.DBdao;
import Obe.Db.Dto.Book;
import Obe.Db.Dto.Information;
import Obe.Db.Dto.Movie;
import Obe.Db.Dto.Tags;
import Obe.Db.Dto.Userbook;
import Obe.Db.Dto.Usermovie;
import Obe.Util.DbInitial;
import Obe.Util.file;

public class DB2SVM_P {
	private static int NUM = 4;
	private static int flag = 0;
	private static HashMap<String,Integer> wordsMap = new HashMap<String,Integer>();
	private static List<Map.Entry<String,Integer>> words = new ArrayList<Map.Entry<String,Integer>>();

	private static String filepath = "F:/work/UserStableMatching/etc/SVM/";

	
	public static void main(String[] args) throws IOException {
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
    				function(A,user,db,sf);
    			}
    			else if(str.equals("stackoverflow")){
    				function(B,user,db,sf);
    			}
//    			else if(str.equals("superuser")){
//    				function(C,user,db,sf);
//    			}    		
    		}
    		int m;
    		m=0;
    	}
    	//for SVM
		//train
    	file f = new file();
		f.setoutpath(filepath+"train/alltrain");f.delete();
		CountCossim cossim = new CountCossim();
    	for(User a:A){
    		for(User b:B){
    			double nameCos = EditDistance.getEditDistance(a.getNickname(),b.getNickname());
    			
    			List<Map.Entry<String,Integer>> ra = countWordList(a.getText());
    			List<Map.Entry<String,Integer>> rb = countWordList(b.getText());   			
    			double textCos = cossim.getCos(ra,rb);
    			
    			double tagsCos = cossim.getCos(a.getTags(),b.getTags());
    			
    			int tagsCom = getCommon(a.getTags(),b.getTags());
    			//double featureCos = cossim.getCos(a.getFeature(),b.getFeature());
    			
    			if(a.getName().split("--")[0].equals(b.getName().split("--")[0])){//正例
    				f.write("+1 "+"1:"+nameCos+" 2:"+textCos+" 3:"+tagsCos+" 4:"+tagsCom+"\r\n");
    			}
    			else{
    				f.write("-1 "+"1:"+nameCos+" 2:"+textCos+" 3:"+tagsCos+" 4:"+tagsCom+"\r\n");
    			}
    		}
    	}
//    	//test
//    	for(User a:B){
//			f.setoutpath(filepath+"test/"+a.getName().split("--")[0]);f.delete();
//			for(User b:B){
//				double nameCos = EditDistance.getEditDistance(a.getNickname(),b.getNickname());
//    			
//    			List<Map.Entry<String,Integer>> ra = countWordList(a.getText());
//    			List<Map.Entry<String,Integer>> rb = countWordList(b.getText());   			
//    			double textCos = cossim.getCos(ra,rb);
//    			
//    			double tagsCos = cossim.getCos(a.getTags(),b.getTags());
//    			
//    			int tagsCom = getCommon(a.getTags(),b.getTags());
//    			//double featureCos = cossim.getCos(a.getFeature(),b.getFeature());
//    			
//    			if(a.getName().split("--")[0].equals(b.getName().split("--")[0])){//正例
//    				f.write("+1 "+"1:"+nameCos+" 2:"+textCos+" 3:"+tagsCos+" 4:"+tagsCom+"\r\n");
//    			}
//    			else{
//    				f.write("-1 "+"1:"+nameCos+" 2:"+textCos+" 3:"+tagsCos+" 4:"+tagsCom+"\r\n");
//    			}
//			}
//    	}
    	System.out.println("SVM　FINISHED");

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

	private static List<Entry<String, Integer>> countWordList(String text) {
		HashMap<String,Integer> result = new HashMap<String,Integer>();
		result = countWord(text, result);
		List<Map.Entry<String,Integer>> r = new ArrayList<Map.Entry<String,Integer>>();
		r.addAll(result.entrySet());
		MapCompare mc = new MapCompare();   
		Collections.sort(r,mc);
		return r;
	}

	private static void function(List<User> A, Information user, DBdao db, SessionFactory sf) throws IOException {
		//for hLDA output
		file f = new file();
		f.setoutpath("F:/work/UserTagsHierarchy/etc/user/"+user.getId().getId()+"---"+user.getId().getSource());f.delete();
		User u = new User();    				
		u.setName(user.getId().getId()+"--"+user.getId().getSource());
		List<Object> ut =  db.getT(sf, "getTags", Integer.valueOf(user.getId().getId()),user.getId().getSource());
		HashMap<String,Integer> result = new HashMap<String,Integer>();
		for(Object t:ut){
			Tags usertag = (Tags)t;
			String s = usertag.getScore();
			String text = usertag.getDescribtion();
			f.write(usertag.getId().getTagName()+" "+InfoParser(text)+"\r\n");
			String tag = usertag.getId().getTagName();
			result.put(tag, Integer.valueOf(scoreParser(s)));
		}
		List<Map.Entry<String,Integer>> r = new ArrayList<Map.Entry<String,Integer>>();
		r.addAll(result.entrySet());
		MapCompare mc = new MapCompare();   
		Collections.sort(r,mc);
		
		u.setNickname(user.getId().getUserName());
		u.setTags(r);
		u.setText(user.getDescribtion());
		A.add(u);		
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
	
	private static String scoreParser(String s){
		if(s.contains("k")){
			s = s.replaceAll("k", "000");
		}
		return s;
	}
	
	//for hLDA
	private static String InfoParser(String text){
        String info = "";
		String[] texts = text.split("\r\n");
        for(String line:texts){
        	if(line.startsWith("<li>"))
        		continue;
        	String str = line.replaceAll("<[^>]*>", "").replaceAll("\\s{1,}", " ").trim();//去<>中内容以及多个空格
        	if(str.equals(" ")||str.equals("")||str.equals("None")||
        			str.contains("There is no tag wiki for this tag")||
        			str.contains("Tag wikis help introduce newcomers to the tag")||
        			str.contains("All registered users may propose new tag wikis")||
        			str.contains("Note that if you have less than 20000 reputation")||
        			str.contains("There is no usage guidance for this tag")||
        			str.contains("Usage guidance, also known as a tag wiki")||
        			str.contains("DO NOT USE THIS TAG")||
        			str.contains("THIS TAG IS BEING ACTIVELY CLEANED UP")||
        			str.contains("Tag Cleanup"))
        		continue;
        	 info += str +" ";
        }
        //System.out.println(info);
        return info;
	}
}
