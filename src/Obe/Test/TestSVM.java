package Obe.Test;

import java.io.File;
import java.io.IOException;

import Obe.SVM.svm_predict;
import Obe.SVM.svm_train;

public class TestSVM {

	private static String trainpath = "D:/Work/UserStableMatching/etc/SVM/train/";
	private static String testpath = "D:/Work/UserStableMatching/etc/SVM/train/";
	private static String resultpath = "D:/Work/UserStableMatching/etc/SVM/result/";

	public static void main(String argv[]) throws IOException{
		double sum = 0.0;
		File dataDirx = new File(trainpath); 
	    File[] dataFilesx  = dataDirx.listFiles();
	    for(int j = 0; j < 1; j++){ 
	    	String id = dataFilesx[0].getPath().split("train\\\\")[1].split("--")[0];
	    	
	    	File file =new File(resultpath+id);
	    	if(!file.exists()&&!file.isDirectory())
	    		file .mkdir();
	    	
	    	String[] arg = { "-t","2","-b","1","-g","0",
	    		trainpath+id, // 存放SVM训练模型用的数据的路径
	    		resultpath+id+"/model_r.txt" }; // 存放SVM通过训练数据训/ //练出来的模型的路径

	    	String[] parg = { "-b","1",
	    		testpath+id, // 这个是存放测试数据
	    		resultpath+id+"/model_r.txt", // 调用的是训练以后的模型
	    		resultpath+id+"/out_r.txt" }; // 生成的结果的文件的路径
	    	
			System.out.println("........SVM STARTING..........");
			// 创建一个训练对象
			svm_train t = new svm_train();
			// 创建一个预测或者分类的对象
			svm_predict p = new svm_predict();
			t.main(arg); // 调用
			double v = p.main(parg); // 调用
			
			System.out.println("+1 ACCUARCY:"+v);
			
			sum += v;
	    }
	    System.out.println("AVAERAGE:"+(double)sum);
	}
}
