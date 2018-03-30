import java.util.ArrayList;

public class LinguisticModules {
	private static PorterStemmer STEMMER = new PorterStemmer();

	public static ArrayList<Pair> modifyTokens(ArrayList<Pair> tuples) {
		ArrayList<Pair> newTuples = new ArrayList<Pair>();
		
		for(Pair p: tuples) {
			String tokenStemmed = STEMMER.stemWord(p.getToken().toLowerCase());
			Pair p2 = new Pair(tokenStemmed, p.getdocId());
			newTuples.add(p2);
		}
		
		return newTuples;
	}
	
	public static ArrayList<String> modifyQueryTokens(ArrayList<String> tokens) {
		ArrayList<String> modifiedTokens = new ArrayList<String>();
		for(String tk: tokens) {
			modifiedTokens.add(STEMMER.stemWord(tk.toLowerCase()));
		}
		return modifiedTokens;
	}
}
