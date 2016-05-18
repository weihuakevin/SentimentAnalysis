package word2vec;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class WordCosine {

	public static float WordCosine(float[] word1, float[] word2) {

		if (word1 == null || word2 == null || word1.length != word2.length) {
			return 0;
		}
		float wordcos = 0;
		float vector1Module = 0;
		float vector2Module = 0;
		for (int i = 0; i < word1.length; i++) {
			vector1Module += word1[i]*word1[i];
			vector2Module += word2[i]*word2[i];
			wordcos += word1[i] * word2[i];
		}
		return (wordcos/(vector1Module*vector2Module));
	}

	public static void main(String[] args) throws IOException {

		Word2VEC vec = new Word2VEC();
		vec.loadGoogleModel("library/vector.bin");
		String str = "���";
		Set<WordEntry> results = vec.distance(str);
		Iterator<WordEntry> iter = results.iterator();
		while (iter.hasNext()) {
			String entry = ((WordEntry) iter.next()).toString();
			System.out.println(entry);
		}
		float[] vector1 = vec.getWordVector("���");
		float[] vector2 = vec.getWordVector("�տ�");
		System.out.println(WordCosine.WordCosine(vector1, vector2));
	}

}
