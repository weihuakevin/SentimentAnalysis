package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import tag.TagJson;
import utils.TagData;
import word2vec.Word2VEC;
import word2vec.Word2VecTest;
import word2vec.WordCosine;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

public class TFPosTagExtract {

	public static class ByValueComparator implements Comparator<String> {
		HashMap<String, Integer> base_map;

		public ByValueComparator(HashMap<String, Integer> base_map) {
			this.base_map = base_map;
		}

		public int compare(String arg0, String arg1) {
			if (!base_map.containsKey(arg0) || !base_map.containsKey(arg1)) {
				return 0;
			}

			if (base_map.get(arg0) < base_map.get(arg1)) {
				return 1;
			} else if (base_map.get(arg0) == base_map.get(arg1)) {
				return 0;
			} else {
				return -1;
			}
		}
	}
	public static void main(String[] args) throws IOException {
		// TODO 自动生成的方法存根

		Word2VEC vec = new Word2VEC();
		vec.loadGoogleModel("library/meituanvectorSize150.bin");
		HashMap<String, Integer> newtag = new HashMap<String,Integer>();
		List<TagData> tag = new ArrayList<TagData>();
		try {

			BufferedReader read=new BufferedReader(new FileReader(new File("traindata/10point")));
			String lin="";
			while((lin=read.readLine())!=null){
				float[] SentenceVector = new float[150];
				List<Term> wordlist = HanLP.segment(lin);
				String[] word = new String[wordlist.size()];
				String[] flag = new String[wordlist.size()];
				int count = 0;
				for(Term t:wordlist){
					String[] wordflag = t.toString().split("/");
					word[count] = wordflag[0];
					flag[count] = wordflag[1];
					count++;

				}
				for(int i=0;i<count-1;i++){
					if((flag[i].equals("n")) && ((flag[i+1].equals("ad")))){
						float[] vector1 = vec.getWordVector(word[i]);
						float[] vector2 = vec.getWordVector(word[i+1]);
						SentenceVector = Word2VecTest.SumVector(vector1, vector2);
						String candidatetag = "";
						candidatetag = word[i] + ""+ word[i+1];
						if(!newtag.containsKey(candidatetag)){
							newtag.put(candidatetag, 1);
						}
						else{
							int num = newtag.get(candidatetag);
							newtag.put(candidatetag, num+1);
						}
					}
					if((flag[i].equals("n")) && ((flag[i+1].equals("a")))){
						float[] vector1 = vec.getWordVector(word[i]);
						float[] vector2 = vec.getWordVector(word[i+1]);
						SentenceVector = Word2VecTest.SumVector(vector1, vector2);
						String candidatetag = "";
						candidatetag = word[i] + ""+ word[i+1];
						if(!newtag.containsKey(candidatetag)){
							newtag.put(candidatetag, 1);
						}
						else{
							int num = newtag.get(candidatetag);
							newtag.put(candidatetag, num+1);
						}
					}
					if((flag[i].equals("nz")) && ((flag[i+1].equals("ad")))){
						float[] vector1 = vec.getWordVector(word[i]);
						float[] vector2 = vec.getWordVector(word[i+1]);
						SentenceVector = Word2VecTest.SumVector(vector1, vector2);
						String candidatetag = "";
						candidatetag = word[i] + ""+ word[i+1];
						if(!newtag.containsKey(candidatetag)){
							newtag.put(candidatetag, 1);
						}
						else{
							int num = newtag.get(candidatetag);
							newtag.put(candidatetag, num+1);
						}
					}
					if((flag[i].equals("n")) && ((flag[i+1].equals("d")) && ((flag[i+2].equals("a"))))){
						float[] vector1 = vec.getWordVector(word[i]);
						float[] vector2 = vec.getWordVector(word[i+1]);
						float[] vector3 = vec.getWordVector(word[i+2]);
						float[] tampleVector =  Word2VecTest.SumVector(vector1, vector2);
						SentenceVector = Word2VecTest.SumVector(tampleVector, vector3);
						String candidatetag = "";
						candidatetag = word[i] + ""+ word[i+1];
						if(!newtag.containsKey(candidatetag)){
							newtag.put(candidatetag, 1);
						}
						else{
							int num = newtag.get(candidatetag);
							newtag.put(candidatetag, num+1);
						}
					}
				}
			}
			ByValueComparator bvc = new ByValueComparator(newtag);
			List<String> keys = new ArrayList<String>(newtag.keySet());
			Collections.sort(keys, bvc);
			int count = 0;
			List<String> taglist = new ArrayList<String>();
			for(String key : keys) {
				String w = TagJson.OneTag2Json(count,key,newtag.get(key).toString());
				System.out.println(key+" "+count);
				count ++;
				taglist.add(w);
			}
			String tags = TagJson.TagList2Json(taglist);
			IOHelper.writer("trainfile/tagjson.txt", tags);
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
