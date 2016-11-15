package Obe.Test;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.supervised.instance.SMOTE;

public class SVM_SMOTE {
	private static String wekaTraintpath = "./etc/SVM/weka/";
	private static String wekaTrainout = "./etc/SVM/weka/out/";

	private static int Id = 77;
	
	public static void main(String[] args) throws IOException {
		File file= new File(wekaTraintpath+Id+"/"+Id+".arff");
        ArffLoader loader = new ArffLoader();
        loader.setFile(file);
	    Instances ins = loader.getDataSet();
	    ins.setClassIndex(ins.numAttributes() - 1);
	    
	    SMOTE convert = new SMOTE();
        String[] options = {"-S", "1", "-P", "1000.0", "-K", "5","-C","0"};
        Instances SmoteInstances = null;
        try {
            convert.setOptions(options);
            convert.setInputFormat(ins);
            SmoteInstances = Filter.useFilter(ins, convert);
        } catch (Exception e) {
            e.printStackTrace();
        }
        

		ArffSaver saver = new ArffSaver();
        saver.setInstances(SmoteInstances);
        try {
            saver.setFile(new File(wekaTrainout+Id+".arff"));
            saver.writeBatch();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int m;
        m=0;
	}
}
