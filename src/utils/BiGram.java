package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import tools.IOHelper;

public class BiGram {

	public static void main(String[] args) throws Exception {

		File file=new File("shopyuyan");
		File[] files=file.listFiles();
		for(File f:files){
			
			BufferedReader reader=new BufferedReader(new FileReader(f));
			String line;
			while((line=reader.readLine()) != null){
				IOHelper.append("trainfile/shopyuyan", line);
			}
			reader.close();
		}
	}

}
