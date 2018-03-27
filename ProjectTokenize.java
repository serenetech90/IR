package information_retrival;
import information_retrival.Pair;

import java.util.ArrayList;
import java.util.Arrays;


public class ProjectTokenize {
	public static ArrayList<Pair> tokenize(String content, String docId) {
	String[] tokens = null;
	ArrayList<Pair> token_docId = new ArrayList<Pair>();
	try {
		tokens = content.split("\\s+");
		Arrays.toString(tokens);
		for(int i=0; i < tokens.length; i++){
			Pair p = new Pair(tokens[i].toString(),docId);
			token_docId.add(p);
		}		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return token_docId;
	}

	public static String getDocId(String fileName) {
		int docId = 0;
		try {
			String[] fileNameBits = fileName.split("\\.");
			docId = Integer.parseInt(fileNameBits[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.toString(docId);
	}
}
