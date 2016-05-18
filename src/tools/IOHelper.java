package tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IOHelper {

	public static String readlabel(String path){
		File file=new File(path);
		String label=null;
		try {
			BufferedReader reader=new BufferedReader(new FileReader(file));
			String line="";
			while((line=reader.readLine())!=null){
				label=line;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return label;
	}
	public static void append(String path, String line){
		File file=new File(path);
		try {
			BufferedWriter writer=new BufferedWriter(new FileWriter(file,true));
			line+="\r\n";
			writer.write(line);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static Set<String> readwords(String path){
		Set<String> docmap=new HashSet<String>();
		File file=new File(path);
		try {
			BufferedReader reader=new BufferedReader(new FileReader(file));
			String line="";
			while((line=reader.readLine())!=null){
				line=line.trim();
				docmap.add(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return docmap;
	}
	public static Set<String> readterms(String path){
		Set<String> docmap=new HashSet<String>();
		File file=new File(path);
		try {
			BufferedReader reader=new BufferedReader(new FileReader(file));
			String line="";
			while((line=reader.readLine())!=null){
				line=line.trim();
				String[] terms=line.split("\t");
				docmap.add(terms[0]);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return docmap;
	}
	public static void writer(String path, String line){
		File file=new File(path);
		try {
			BufferedWriter writer=new BufferedWriter(new FileWriter(file));
			writer.write(line);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static Map<String,String> read(String path){
		Map<String,String> docmap=new HashMap<String,String>();
		File file=new File(path);
		try {
			BufferedReader reader=new BufferedReader(new FileReader(file));
			String line="";
			while((line=reader.readLine())!=null){
				docmap.put(line, "1");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return docmap;
	}
	public static Map<String,Double> readwordidf(String path){
		Map<String,Double> docmap=new HashMap<String,Double>();
		File file=new File(path);
		try {
			BufferedReader reader=new BufferedReader(new FileReader(file));
			String line="";
			while((line=reader.readLine())!=null && line.length()>0){
				String[] wordidf=line.split(":");
				docmap.put(wordidf[0], Double.valueOf(wordidf[1]));
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return docmap;
	}
	public static Map<String,String> readwordnum(String path){
		Map<String,String> docmap=new HashMap<String,String>();
		File file=new File(path);
		try {
			BufferedReader reader=new BufferedReader(new FileReader(file));
			String line="";
			while((line=reader.readLine())!=null){
				String[] wordnum=line.split("\t");
				docmap.put(wordnum[0], wordnum[1]);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return docmap;
	}
}
