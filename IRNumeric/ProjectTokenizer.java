import java.util.ArrayList;

public class ProjectTokenizer {

	public static ArrayList<Pair> tokenizeCorpus(ArrayList<String> fileList) {
		ArrayList<Pair> token_docId = new ArrayList<Pair>();
		
		for(int i=0, n=fileList.size(); i<n; i++) {
			String content = ProjectToolkit.readTextFile(fileList.get(i));
			ArrayList<Pair> token_docIdTemp = tokenize(content, i);
			token_docId.addAll(token_docIdTemp);
		}
		return token_docId;
	}
	
	public static ArrayList<Pair> tokenize(String content, int docId) {
		ArrayList<Pair> token_docId = new ArrayList<Pair>();
		String[] tokens = content.split("\\s+");
		for(String tk: tokens) {
			//tk = tk.replaceAll("[^a-zA-Z0-9]", "");
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
			//tk = tk.replaceAll("[^a-zA-Z0-9]", "");
			if(!tk.isEmpty()) {
				validTokens.add(tk);
			}
		}
		return validTokens;
	}
}
