package information_retrival;

import java.util.ArrayList;

public class LinguisticModules {

	public static ArrayList<Pair> rmSymbolLower(ArrayList<Pair> tuple) {
		ArrayList<Pair> tuple_new = new ArrayList<Pair>();
		try {
			for(int i=0; i < tuple.size(); i++){ 
				Pair p = new Pair(((Pair) tuple.get(i)).getToken().replaceAll("[^a-zA-Z0-9]", "").toLowerCase(),((Pair) tuple.get(i)).getdocId());
				tuple_new.add(p);
			}							
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tuple_new;
		}

	public static ArrayList<Pair> stemming(ArrayList<Pair> tuple) {
		ArrayList<Pair> tuple_new = new ArrayList<Pair>();
		PorterStemmer porterStemmer = new PorterStemmer();
		
		try {
			for(int i=0; i < tuple.size(); i++){
				String stem = porterStemmer.stemWord(((Pair) tuple.get(i)).getToken());
				if(stem.isEmpty()) i++;
				else{
					Pair p = new Pair(stem,((Pair) tuple.get(i)).getdocId());
					tuple_new.add(p);					
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tuple_new;
		}
}
