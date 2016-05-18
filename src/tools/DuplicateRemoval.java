package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class DuplicateRemoval {

	public static void Removal(String inputPath, String outputPath){
		File file=new File(inputPath);
		HashSet<String> AfterRemoval = new HashSet<String>();
		try {
			BufferedReader reader=new BufferedReader(new FileReader(file));
			String line="";
			while((line=reader.readLine())!=null){
				AfterRemoval.add(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String ar:AfterRemoval){
			IOHelper.append(outputPath, ar);
		}
	}
	public static void main(String[] args) {

		Removal("traindata/WangyiNag.txt", "traindata/WangyiNagAfter.txt");
		Removal("traindata/WangyiPos.txt", "traindata/WangyiPosAfter.txt");
	}

}
