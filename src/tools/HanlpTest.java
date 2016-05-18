package tools;

import java.io.IOException;

import word2vec.Word2VEC;
import word2vec.Word2VecTest;

public class HanlpTest {

	public static void main(String[] args) throws IOException {
		// TODO 自动生成的方法存根

		Word2VEC vec = new Word2VEC();
		vec.loadGoogleModel("library/meituanvectorSize250.bin");
		float[] vector1 = vec.getWordVector("味道");
		float[] vector2 = vec.getWordVector("很好");
		float[] SentenceVector = Word2VecTest.SumVector(vector1, vector2);
		String out = "";
		for(int i=0;i<SentenceVector.length;i++){
			out += SentenceVector[i] + " ";
		}
		System.out.println(out);
	}

}
