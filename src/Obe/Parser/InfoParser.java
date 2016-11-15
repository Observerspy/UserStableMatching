package Obe.Parser;

import java.io.File;

import Obe.Util.LoadBigFile;
import Obe.Util.file;

public class InfoParser {
	public static String InfoPath = "F:/work/UserTagsHierarchy/etc/info/";

	public static void main(String[] args) throws Exception {
		File dataDirx = new File(InfoPath); 
	    File[] dataFilesx  = dataDirx.listFiles();
	    for(int j = 0; j < dataFilesx.length; j++){ 
	    	String source = "";
	    	if(dataFilesx[j].getPath().contains("programmers"))
	        	source = "programmers";
	        else if (dataFilesx[j].getPath().contains("stackoverflow"))
	        	source = "stackoverflow";
	        else if (dataFilesx[j].getPath().contains("superuser"))
	        	source = "superuser";
	    	
	    	file f = new file();
	    	f.setoutpath("./etc/result/"+source+".txt");f.delete();
	    	
			File dataDir = new File(InfoPath+source+"/"); 
		    File[] dataFiles  = dataDir.listFiles(); 
		    for(int i = 0; i < dataFiles.length; i++){ 
		        LoadBigFile l = new LoadBigFile();
		        l.setPath(dataFiles[i].getPath());
		        String text = l.Load();	        
		        String info = "";
		        String[] texts = text.split("\r\n");
		        for(String line:texts){
		        	if(line.startsWith("<li>"))
		        		continue;
		        	String str = line.replaceAll("<[^>]*>", "").replaceAll("\\s{1,}", " ").trim();//去<>中内容以及多个空格
		        	if(str.equals(" ")||str.equals("")||
		        			str.contains("There is no tag wiki for this tag")||
		        			str.contains("Tag wikis help introduce newcomers to the tag")||
		        			str.contains("All registered users may propose new tag wikis")||
		        			str.contains("Note that if you have less than 20000 reputation")||
		        			str.contains("There is no usage guidance for this tag")||
		        			str.contains("Usage guidance, also known as a tag wiki")||
		        			str.contains("DO NOT USE THIS TAG")||
		        			str.contains("Tag Cleanup"))
		        		continue;
		        	 info += str +" ";
		        }
		        String[] strs = dataFiles[i].getPath().split("\\\\");
		        String tag = strs[strs.length-1].split("\\.txt")[0];
		        //System.out.println("========"+source+"========");
		        f.write("TAGS:"+tag+"\r\n"+info+"\r\n");
		        System.out.println("NUM: "+(i+1)+" TAG: "+tag+" Info: \r\n"+info);
		    }
	    }

	}

}
