package Obe.Test;

import java.io.IOException;

import Obe.Util.LoadBigFile;
import Obe.Util.file;

public class SV2Train {
	private static String filepath = "F:/work/UserStableMatching/etc/SVM/train";

	
	public static void main(String[] args) throws Exception {
		LoadBigFile l = new LoadBigFile();
        l.setPath(filepath+"/77ex");
        String text = l.Load();	
        String[] texts = text.split("\r\n");
        
        file f = new file();
		f.setoutpath(filepath+"/77-sv");f.delete();
        for(String str:texts){
        	String val = str.split(" ",2)[0];
        	String t = str.split(" ",2)[1];
        	Double v = Double.valueOf(val);
        	if(v>0){
        		f.write("+1 "+t+"\r\n");
        	}
        	else if(v<0){
        		f.write("-1 "+t+"\r\n");
        	}
        }
	}
}
