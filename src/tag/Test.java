package tag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import tools.IOHelper;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dependency.CRFDependencyParser;

public class Test {  

	public static void main(String[] args) throws IOException {      
		BufferedReader reader=new BufferedReader(new FileReader(new File("traindata/haoping.txt")));
		String line;
		int i = 0;
		while((line=reader.readLine()) != null){
			System.out.println(line);
			String path = "SampleWangYi/pos/";
			path += i +".txt";
			IOHelper.writer(path, line);
			i++;
		}
	}
}