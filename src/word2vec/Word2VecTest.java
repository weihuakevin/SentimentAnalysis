package word2vec;

import java.io.IOException;

public class Word2VecTest {

	public static String Vector2String(float[] v){
		if(v == null){
			return null;
		}
		String vector = "";
		for(int i=0;i<v.length;i++){
			vector += v[i] + " ";
		}
		return vector;
	}
	public static float[] SumVector(float[] v1, float[] v2){
		if(v1==null && v2==null){
			return null;
		}
		if(v1==null && v2!=null){
			return v2;
		}
		if(v1!=null && v2==null){
			return v1;
		}
		float[] v = new float[v1.length];
		for(int i=0;i<v1.length;i++){
			v[i] = (float) v1[i] + (float) v2[i];
		}
		return v;
	}
	public static void main(String[] args) throws IOException {

		Word2VEC vec = new Word2VEC();
		vec.loadGoogleModel("library/meituanvectors.bin");
		float[] vector1 = vec.getWordVector("中国");
		float[] vector2 = vec.getWordVector("美国");
		System.out.println(WordCosine.WordCosine(vector1, vector2));
		System.out.println(Vector2String(vector1));
		System.out.println(Vector2String(vector2));
		System.out.println(Vector2String(SumVector(vector1,vector2)));
	}

}
