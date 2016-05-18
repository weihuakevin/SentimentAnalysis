package org.svm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import tools.DocData2SVMModel;
import tools.IOHelper;
import utils.LabelProbability;
import word2vec.Word2VEC;
import word2vec.Word2VecTest;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

public class ShopTest {

	public static void main(String[] args) throws Exception {

		Word2VEC vec = new Word2VEC();
		vec.loadGoogleModel("library/meituanvectorSize150.bin");
		
		Predict svmpredict = new Predict();
		svmpredict.LoadSvmModel("trainfile\\model.txt");
		
		HashMap<String,Double> NegReviews = new HashMap<String, Double>();//差评及其概率
		HashMap<String,Double> PosReviews = new HashMap<String, Double>();//好评及其概率
//		HashMap<String,Double> OtherReviews = new HashMap<String, Double>();//中评及其概率
		BufferedReader reader=new BufferedReader(new FileReader(new File("traindata/WangyiNews.txt")));
		String line;
		long starTime=System.currentTimeMillis();
		while((line=reader.readLine()) != null){

			float[] SentenceVector = new float[50];
			List<Term> wordlist = HanLP.segment(line);
			for(Term t:wordlist){
				String[] wordflag = t.toString().split("/");
				float[] vector = vec.getWordVector(wordflag[0]);
				SentenceVector = Word2VecTest.SumVector(SentenceVector, vector);
			}
			String output = DocData2SVMModel.DataProcess4SVM("10",SentenceVector);
			IOHelper.writer("trainfile/test.txt", output);
			String []parg={"-b","1","trainfile\\test.txt","trainfile\\model.txt"};
			LabelProbability LabelAndProbability = svmpredict.LabelProbabilityPredict(parg); 
			if(LabelAndProbability.getLabel().equals("10.0")){
				NegReviews.put(line, LabelAndProbability.getProbability());
			}
			else if(LabelAndProbability.getLabel().equals("50.0")){
				PosReviews.put(line, LabelAndProbability.getProbability());
			}
//			else{
//				PosReviews.put(line, LabelAndProbability.getProbability());
//			}
//			System.out.println(LabelAndProbability.getLabel()+" "+LabelAndProbability.getProbability());
			
		}
		long endTime=System.currentTimeMillis();
		long Time=endTime-starTime;
		System.out.println("程序运行时间：  "+Time);
		
		List<Map.Entry<String, Double>> Neg_list_Data = new ArrayList<Map.Entry<String, Double>>(NegReviews.entrySet());  
		Collections.sort(Neg_list_Data, new Comparator<Map.Entry<String, Double>>()  
		{    
			public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2)  
			{  
				if ((o2.getValue() - o1.getValue())>0)  
					return 1;  
				else if((o2.getValue() - o1.getValue())==0)  
					return 0;  
				else   
					return -1;  
			}  
		});
		List<Map.Entry<String, Double>> Pos_list_Data = new ArrayList<Map.Entry<String, Double>>(PosReviews.entrySet());  
		Collections.sort(Pos_list_Data, new Comparator<Map.Entry<String, Double>>()  
		{    
			public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2)  
			{  
				if ((o2.getValue() - o1.getValue())>0)  
					return 1;  
				else if((o2.getValue() - o1.getValue())==0)  
					return 0;  
				else   
					return -1;  
			}  
		});
//		List<Map.Entry<String, Double>> Other_list_Data = new ArrayList<Map.Entry<String, Double>>(OtherReviews.entrySet());  
//		Collections.sort(Pos_list_Data, new Comparator<Map.Entry<String, Double>>()  
//		{    
//			public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2)  
//			{  
//				if ((o2.getValue() - o1.getValue())>0)  
//					return 1;  
//				else if((o2.getValue() - o1.getValue())==0)  
//					return 0;  
//				else   
//					return -1;  
//			}  
//		});
		for(Entry<String, Double> map:Neg_list_Data){
//			System.out.println(map);
			IOHelper.append("traindata/wangyiNag.txt", map.getKey());
		}
		System.out.println();
		for(Entry<String, Double> map:Pos_list_Data){
//			System.out.println(map);
			IOHelper.append("traindata/wangyiPos.txt", map.getKey());
		}
		System.out.println();
//		for(Entry<String, Double> map:Other_list_Data){
//			System.out.println(map);
//			IOHelper.append("traindata/WangyOther.txt", map.getKey());
//		}
	}

}
