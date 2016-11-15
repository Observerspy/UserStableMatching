package Obe.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import Obe.Util.LoadBigFile;
import Obe.Util.file;

public class Data2Arff {
	private static String str = "train";
	private static String trainpath = "D:/Work/UserStableMatching/etc/SVM/"+str+"/";
	private static String wekaTraintpath = "D:/Work/UserStableMatching/etc/SVM/weka-"+str+"/";
	private static int Num = 2;
	
	public static void main(String[] args) throws Exception {
		File dataDirx = new File(trainpath); 
	    File[] dataFilesx  = dataDirx.listFiles();
	    for(int j = 0; j < dataFilesx.length; j++){ 
	    	String id = dataFilesx[j].getPath().split(str+"\\\\")[1];
	    	
	    	File file =new File(wekaTraintpath+id);
	    	if(!file.exists()&&!file.isDirectory())
	    		file .mkdir();
	    	
	    	file f = new file();
			f.setoutpath(wekaTraintpath+id+"/"+id+"-t.arff");f.delete();
			f.write("@relation SiftFeature\r\n");
			for(int i=0;i<Num;i++){
				f.write("@attribute "+i+" NUMERIC\r\n");
			}
			f.write("@attribute 'Class' {'+1','-1'}\r\n");
			f.write("@data\r\n");

	    	LoadBigFile load = new LoadBigFile();
			load.setPath(trainpath+id);
			String text = load.Load();
			String[] texts = text.split("\r\n");
			
			for(int i=0;i<texts.length;i++){
				String str = texts[i];
				if(!str.equals("")){
					String line = "";
					line += "{";
					String target = str.split(" ",2)[0];
					String[] strs = str.split(" ",2)[1].split(" ");
					HashMap<Integer,String> map = new HashMap<Integer,String>();
					List<Integer> l = new ArrayList<Integer>();
					for(String s:strs){
						//日 还要按顺序，明天再写
						int Id = -1;
						try{
						Id = Integer.valueOf(s.split(":")[0])-1;
						}catch(Exception e){
							System.out.println("ERROR");
						}
						String val = s.split(":")[1];
						map.put(Id, val);
						l.add(Id);
						//line += Id+" "+val+",";
					}
					
					Collections.sort(l);
					for(Integer x:l){
						line += x+" "+map.get(x)+",";
					}
					line += Num+" "+target;
					line += "}";
					f.write(line+"\r\n");
				}
			}
			
		    System.out.println("prepocessing finished!");
	    }
	}

}
