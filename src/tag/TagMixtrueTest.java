package tag;

import java.io.IOException;

import word2vec.Word2VEC;

public class TagMixtrueTest {

	public static void main(String[] args) throws IOException {

		Word2VEC vec = new Word2VEC();
		vec.loadGoogleModel("library/meituanvectorSize250.bin");
	}

}
