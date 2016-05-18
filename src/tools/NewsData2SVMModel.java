package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import word2vec.Word2VEC;
import word2vec.Word2VecTest;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

public class NewsData2SVMModel {

	/**
	 * 下面的方法用于给SVM提供数据,一共有三个
	 * @param label
	 * @param v
	 * @return
	 */
	public static String DataProcess4SVM(String label,float[] v){
		if(v == null){
			return label;
		}
		String reback = label;
		for(int i=0;i<v.length;i++){
			reback += " " + i +":"+ v[i];
		}
		return reback;
	}
	/**
	 * 下面的方法用于给LR提供数据
	 * @param label
	 * @param v
	 * @return
	 */
	public static String DataProcess4LR(String label,float[] v){
		if(v == null){
			return label;
		}
		String reback = "";
		for(int i=0;i<v.length;i++){
			reback += v[i] + " ";
		}
		reback += label;
		return reback;
	}
	public static void main(String[] args) throws IOException {
		// TODO 自动生成的方法存根

		Word2VEC vec = new Word2VEC();
		vec.loadGoogleModel("library/meituanvectorSize150.bin");
		File file1=new File("traindata/chaping.txt");
		BufferedReader reader1=new BufferedReader(new FileReader(file1));
		String line1;
		while((line1=reader1.readLine()) != null){
			if(line1.length() > 1){
				String[] LineAndScore = line1.split("\t");
				float[] SentenceVector = new float[150];
				List<Term> wordlist = HanLP.segment(LineAndScore[0]);
				for(Term t:wordlist){
					String[] wordflag = t.toString().split("/");
					float[] vector = vec.getWordVector(wordflag[0]);
					SentenceVector = Word2VecTest.SumVector(SentenceVector, vector);
				}
				String output = DataProcess4SVM("1",SentenceVector);
				IOHelper.append("trainfile/traindata", output);
			}
		}
		reader1.close();
		
		File file2=new File("traindata/haoping.txt");
		BufferedReader reader2=new BufferedReader(new FileReader(file2));
		String line2;
		while((line2=reader2.readLine()) != null){
			if(line2.length() > 1){
				String[] LineAndScore = line2.split("\t");
				float[] SentenceVector = new float[150];
				List<Term> wordlist = HanLP.segment(LineAndScore[0]);
				for(Term t:wordlist){
					String[] wordflag = t.toString().split("/");
					float[] vector = vec.getWordVector(wordflag[0]);
					SentenceVector = Word2VecTest.SumVector(SentenceVector, vector);
				}
//				System.out.println(Word2VecTest.Vector2String(SentenceVector));
//				System.out.println(DataProcess4SVM("50",SentenceVector));
				String output = DataProcess4SVM("3",SentenceVector);
				IOHelper.append("trainfile/traindata", output);
			}
		}
		reader2.close();
		
		File file3=new File("traindata/zhongping.txt");
		BufferedReader reader3=new BufferedReader(new FileReader(file3));
		String line3;
		while((line3=reader3.readLine()) != null){
			if(line3.length() > 1){
				String[] LineAndScore = line3.split("\t");
				float[] SentenceVector = new float[150];
				List<Term> wordlist = HanLP.segment(LineAndScore[0]);
				for(Term t:wordlist){
					String[] wordflag = t.toString().split("/");
					float[] vector = vec.getWordVector(wordflag[0]);
					SentenceVector = Word2VecTest.SumVector(SentenceVector, vector);
				}
//				System.out.println(Word2VecTest.Vector2String(SentenceVector));
//				System.out.println(DataProcess4SVM("50",SentenceVector));
				String output = DataProcess4SVM("2",SentenceVector);
				IOHelper.append("trainfile/traindata", output);
			}
		}
		reader3.close();
	}
}
