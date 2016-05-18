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

public class DocData2SVMModel {

	/**
	 * 下面的方法用于给SVM提供数据
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
		
		File file=new File("traindata/chaping.txt");
//		File[] files=file.listFiles();
//		for(File f:files){
			
			BufferedReader reader=new BufferedReader(new FileReader(file));
			String line;
			while((line=reader.readLine()) != null){
				
				if(line.length() > 1){
					String[] LineAndScore = line.split("\t");
					float[] SentenceVector = new float[150];
					List<Term> wordlist = HanLP.segment(LineAndScore[0]);
					for(Term t:wordlist){
						String[] wordflag = t.toString().split("/");
						float[] vector = vec.getWordVector(wordflag[0]);
						SentenceVector = Word2VecTest.SumVector(SentenceVector, vector);
					}
					String output = DataProcess4SVM("10",SentenceVector);
					IOHelper.append("trainfile/traindata", output);
				}
			}
			reader.close();
//		}
		
		File file1=new File("traindata/haoping.txt");
//		File[] files1=file1.listFiles();
//		for(File f:files1){
			
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
					String output = DataProcess4SVM("50",SentenceVector);
					IOHelper.append("trainfile/traindata", output);
				}
			}
			reader1.close();
//		}
		
//		File file1=new File("traindata/10point");
//		BufferedReader reader1=new BufferedReader(new FileReader(file1));
//		String line1;
//		while((line1=reader1.readLine()) != null){
//			if(line1.length() > 1){
//				String[] LineAndScore = line1.split("\t");
//				float[] SentenceVector = new float[1150];
//				List<Term> wordlist = HanLP.segment(LineAndScore[0]);
//				for(Term t:wordlist){
//					String[] wordflag = t.toString().split("/");
//					float[] vector = vec.getWordVector(wordflag[0]);
//					SentenceVector = Word2VecTest.SumVector(SentenceVector, vector);
//				}
//				String output = DataProcess4SVM("10",SentenceVector);
//				IOHelper.append("trainfile/traindata", output);
//			}
//		}
//		reader1.close();
//		
//		File file2=new File("traindata/150point");
//		BufferedReader reader2=new BufferedReader(new FileReader(file2));
//		String line2;
//		while((line2=reader2.readLine()) != null){
//			if(line2.length() > 1){
//				String[] LineAndScore = line2.split("\t");
//				float[] SentenceVector = new float[1150];
//				List<Term> wordlist = HanLP.segment(LineAndScore[0]);
//				for(Term t:wordlist){
//					String[] wordflag = t.toString().split("/");
//					float[] vector = vec.getWordVector(wordflag[0]);
//					SentenceVector = Word2VecTest.SumVector(SentenceVector, vector);
//				}
//				String output = DataProcess4SVM("150",SentenceVector);
//				IOHelper.append("trainfile/traindata", output);
//			}
//		}
//		reader2.close();
	}
}
