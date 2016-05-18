package org.svm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import tools.DocData2SVMModel;
import tools.IOHelper;
import utils.LabelProbability;
import word2vec.Word2VEC;
import word2vec.Word2VecTest;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

public class FileListTest {

	public static void main(String[] args) throws Exception {

		Word2VEC vec = new Word2VEC();
		vec.loadGoogleModel("library/meituanvectorSize250.bin");
		
		int pos2pos=0;
		int pos2neg=0;
		int neg2pos=0;
		int neg2neg=0;
		int filecount1=0;
		int filecount2=0;
		
		File file=new File("Sample/pos");
		File[] files=file.listFiles();
		for(File f:files){
			
			BufferedReader reader=new BufferedReader(new FileReader(f));
			String line;
			while((line=reader.readLine()) != null){
				
				float[] SentenceVector = new float[250];
				List<Term> wordlist = HanLP.segment(line);
				for(Term t:wordlist){
					String[] wordflag = t.toString().split("/");
					float[] vector = vec.getWordVector(wordflag[0]);
					SentenceVector = Word2VecTest.SumVector(SentenceVector, vector);
				}
				System.out.println(Word2VecTest.Vector2String(SentenceVector));
				System.out.println(DocData2SVMModel.DataProcess4SVM("50",SentenceVector));
				String output = DocData2SVMModel.DataProcess4SVM("50",SentenceVector);
				IOHelper.writer("trainfile/test.txt", output);
				String []parg={"-b","1","trainfile\\test.txt","trainfile\\model.txt"};
				LabelProbability accuracy=Predict.LabelProbabilityPredict(parg); 
				System.out.println(accuracy);
			}
		}
	}

}
