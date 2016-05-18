package tag;

import java.util.ArrayList;
import java.util.List;

public class TagJson {

	public static String TagList2Json(List<String> taglist){
		String reback = "{"+"\"name\":"+"\""+"tag"+"\""+","
				+"\"children\"" +":"+"[";
		int taglength = taglist.size();
		int tagcount = 0;
		for(String tag:taglist){
			if(tagcount < taglength-1){
				reback += tag +",";
			}
			else{
				reback += tag;
			}
			tagcount ++;
		}
		reback += "]}";
		return reback;
	}
	public static String OneTag2Json(int i, String tag, String tagnumber){
		String reback ="{"+"\"name\":"+"\""+String.valueOf(i)+"\""+","
						+"\"children\"" +":"+"[{"+"\"name\":"+"\""+tag+"\""+","+"\"size\""+":"+tagnumber+"}]}";
		return reback;
	}
	public static void main(String[] args) {

		String w = OneTag2Json(1,"水电工","123");
		String a = OneTag2Json(2,"电工","223");
		String s = OneTag2Json(3,"水dsd工","323");
		String d = OneTag2Json(4,"水电ghh工","423");
		List<String> taglist = new ArrayList<String>();
		taglist.add(w);
		taglist.add(a);
		taglist.add(s);
		taglist.add(d);
		String tag = TagList2Json(taglist);
		System.out.print(tag);
	}

}
