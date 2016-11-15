package Obe.Cluster.Dao;
/**
 * 余弦相似度接口
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;


public class CountCossim {
	private Vector<String> map1 = new Vector<String>();//时间片1wordmap,更新后的也存在这里
	private Vector<String> map2 = new Vector<String>();//时间片2wordmap
	private Vector<Double> phi1 = new Vector<Double>();//时间片1phi，更新后的也在这里
	private Vector<Double> phi2 = new Vector<Double>();//时间片2phi
	private Vector<Double> temp;//时间片2扩展调整的phi
	
	/**
	 * 功能：计算余弦相似度方法入口
	 */
	public double getCos(List<Map.Entry<String,Integer>> topic1, List<Map.Entry<String,Integer>> topic2) {
		map1.clear();map2.clear();phi1.clear();phi2.clear();
		initial(topic1,topic2,1);
        //合并map为map1，调整phi
		adjust();
		//计算phi1和temp相似度
		double val = count();

		return val;
	}
	
	public double getCosString(String topic1, String topic2) {
		map1.clear();map2.clear();phi1.clear();phi2.clear();

		//initial(topic1,topic2,0);
        //合并map为map1，调整phi
		adjust();
		//计算phi1和temp相似度
		double val = count();

		return val;
	}

	/**
	 * 功能：初始化向量
	 */
	private void initial(List<Map.Entry<String,Integer>> topic1, List<Map.Entry<String,Integer>> topic2,int key) {	
		if(key==1){
			List<String> tempmap = new ArrayList<String>();
			List<Double> tempP = new ArrayList<Double>();
			
			for(Iterator<Map.Entry<String,Integer>> it=topic1.iterator();it.hasNext();){
				Map.Entry entry = (Map.Entry) it.next();
				String app = (String) entry.getKey();			
				Integer val = (Integer) entry.getValue();
				tempmap.add(app);
				tempP.add(Double.valueOf(val));
			}
			
			map1.addAll(tempmap);
		    phi1.addAll(tempP);
		    
		    tempmap.clear();
		    tempP.clear();
		    
		    for(Iterator<Map.Entry<String,Integer>> it=topic2.iterator();it.hasNext();){
				Map.Entry entry = (Map.Entry) it.next();
				String app = (String) entry.getKey();			
				Integer val = (Integer) entry.getValue();
				tempmap.add(app);
				tempP.add(Double.valueOf(val));
			}
		    map2.addAll(tempmap);
		    phi2.addAll(tempP);
		}

	}

	/**
	 * 功能：计算余弦相似度
	 */
	private double count() {
		double sum = 0.0;
		for(int i=0;i<phi1.size();i++){

			sum = sum + phi1.get(i)*temp.get(i);

		}
		double mod1 = getMod(phi1);
		double mod2 = getMod(temp);

		double val = (double)sum/(double)(mod1*mod2);
		return val;
	}
	
	/**
	 * 功能：计算向量模
	 */
	private double getMod(Vector<Double> phi){
		double sum = 0.0;
		for(int i=0;i<phi.size();i++){
			sum = sum + Math.pow(phi.get(i),2);
		}
		return Math.sqrt(sum);
	}
	/**
	 * 功能：扩展合并wordmap至map1，扩展phi1，同时phi2扩展至temp
	 */
	private void adjust() {
		temp = new Vector<Double>(phi1.size(),10);
		for(int i=0;i<phi1.size();i++)
			temp.add(0.0);
		for(int i=0;i<map2.size();i++){
			int flag = 0;
			for(int k=0;k<map1.size();k++){
				if(map2.get(i).equals(map1.get(k))){
					//map2 i单词存在于map1,将phi2 i值拷贝到temp i
					temp.set(k, phi2.get(i));
					flag = 1;
					break;
				}
			}
			//不存在，将该词添加至map1末尾，phi1增加0值末尾，将phi2 i值拷贝到temp 末尾
			if(flag==0){
				map1.add(map2.get(i));
				phi1.add(0.0);
				temp.add(phi2.get(i));	
			}
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
}
