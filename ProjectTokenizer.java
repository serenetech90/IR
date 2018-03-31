import java.util.ArrayList;

public class ProjectTokenizer {

	public static ArrayList<Pair> tokenizeCorpus(String corpusDir) {
		ArrayList<Pair> token_docId = new ArrayList<Pair>();
		ArrayList<String> fileList = ProjectToolkit.listDir(corpusDir);
		for(String fl: fileList) {
			String content = ProjectToolkit.readTextFile(fl);
			ArrayList<Pair> token_docIdTemp = tokenize(content, fl);
			token_docId.addAll(token_docIdTemp);
		}
		return token_docId;
	}
	
	public static ArrayList<Pair> tokenize(String content, String docId) {
		ArrayList<Pair> token_docId = new ArrayList<Pair>();
		String[] tokens = content.split("\\s+");
		for(String tk: tokens) {
			/*
			tk = tk.replaceAll("[^a-zA-Z0-9]", "");
			if(!tk.isEmpty()) {
				Pair p = new Pair(tk, docId);
				token_docId.add(p);
			}
			*/
			if(!tk.isEmpty()) {
				Pair p = new Pair(tk, docId);
				token_docId.add(p);
			}
		}
		return token_docId;
	}
	
	public static ArrayList<String> tokenizeQuery(String content) {
		ArrayList<String> validTokens = new ArrayList<String>();
		String[] tokens = content.split("\\s+");
		for(String tk: tokens) {
			/*
			tk = tk.replaceAll("[^a-zA-Z0-9]", "");
			if(!tk.isEmpty()) {
				validTokens.add(tk);
			}
			*/
			if(!tk.isEmpty()) {
				validTokens.add(tk);
			}
		}
		return validTokens;
	}
}
