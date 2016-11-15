package Obe.Test;

import Obe.Util.LoadBigFile;
import Obe.Util.file;

public class arff2SVM {
	private static String wekaTraintpath = "F:/work/UserStableMatching/etc/SVM/weka-train/lifespec/";
	private static String Id = "lifespec.arff";
	
	public static void main(String[] args) throws Exception {
		LoadBigFile load = new LoadBigFile();
		load.setPath(wekaTraintpath+Id);
		String text = load.Load();
		String[] texts = text.split("\r\n");
		
    	file f = new file();
		f.setoutpath(wekaTraintpath+"lifespec");
		
		for(int i=0;i<texts.length;i++){
			if(texts[i].startsWith("{"))
				continue;
			String[] strs = texts[i].split(",");
			String line = "";
			line += strs[strs.length-1]+" ";
			for(int j=0;j<strs.length-1;j++){
				String x = strs[j];
				try{
				if(Double.valueOf(x)!=0){
					line += (j)+":"+x+" ";
				}
				}catch(Exception e){
					System.out.println("err");
				}
			}
			line += "\r\n";
			f.write(line);
		}
		
		System.out.println("finished");
	}

}
