package information_retrival;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class sortTokens {
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String,LinkedList> main(ArrayList<Pair> list) {
		// TODO Auto-generated method stub
		// Sorting tokens
				
		class comparator implements Comparator <Pair>{
			@Override
			public int compare(Pair o1, Pair o2) {
				// TODO Auto-generated method stub
				if ( o1.getToken().compareTo(o2.getToken())<0)
					return -1; 
				else if ( o1.getToken().compareTo(o2.getToken())>0)
					return 1;				
				else if( o1.getdocId().compareTo(o2.getdocId())<0)
					return -1;
				else if( o1.getdocId().compareTo(o2.getdocId())>0)
					return 1;
				return 0;
			}			
		}
		
		comparator c = new comparator();
		list.sort(c);
		Map<String,LinkedList> index = new LinkedHashMap<String,LinkedList>();
		
		
		int j = 0;
		ArrayList ll = new ArrayList<>();
		Entry<String,Integer> entry ;
		for( int i = 0; i < list.size()-2; i = j) {
			
			entry = new AbstractMap.SimpleEntry(list.get(i).getToken(), 1) ; 
			
			ll.add(list.get(i).getdocId());
			// For producing term frequencies (collapsing duplicate tokens) : best case (all unique tokens) = O(1) (inner do-while will not be executed)
			// average case: let m = most frequent token then it is O(n*m)
			if(entry.getKey().equals(list.get(i+1).getToken()) ) {
				j = i+1;
				// O(m) where m << n in practical cases there won't be an entire index all of same token keyword				
				do {
					entry.setValue(entry.getValue()+1);
					ll.add(list.get(j+1).getdocId());
					++j;
				}while(entry.getKey().equals(list.get(j).getToken()));
				
			}
			ll.add(0, entry.getValue());
			index.put(entry.getKey(),new LinkedList(ll)) ;
			
			ll.clear();
			//ll = new LinkedList<>();
			j++;
			
		}
		
		//Print Map contents
//		System.out.println(index.keySet().toString());
//		System.out.println(index.values().toString());	
		System.out.println("Sorting index is done !");	
		return index;
	}

}

//@Serene
// Notes on Data structure choice and comparisons 
 // Transformation into postings list 
// Transform list to map
// ArrayList class handles dynamic size array and it is more efficient than linkedList 
// in terms of retrieving specific index value
// it has constant-time access O(1)
// LinkedList is used to store the doc_ids and later for merging step, its main benefit is that insertion costs O(1)
// artifact of Java HashMap is that it does not maintain same insertion order as the hashtable size grows up
// Reference to hashMap variable order of iteration over elements: 
// hence it does not comply with our case where we need index to be sorted alphabetically 
// Use LinkedHashMap instead as it maintains insertion order of tokens which are already sorted beforehand
// However, LinkedHashMap consumes more memory as it assigns two pointer (previous+next) for every Entry in the hash map
// LinkedHashMap implements a doubly-linked map ==> consumes more memory.
// Solution : add -XX:+UseCompressedOops JVM variable		
// when running this program for reducing down the memory size used for pointers from 64-bit to 32-bit
// memory address in pointers is no longer machine-native. 		

// @Serene
// Why choosing LinkedList instead of ArrayList for storing postings
// for the constant time complexity O(1) when adding every new doc_id as an element as a posting.
// NOTE: Reasons why to consider ArrayList as another option instead of LinkedList: 
// For the sake of retrieving any documents that hit the search query by accessing doc_ids from the postings list , ArrayList allows random access / constant-time
// access while LinkedList, although it has the ids stored orderly, still we have to iterate sequentially over the linkedLIst elements
// and worst case scenario is that our related document occurs at the end , hence access complexity == O(n), 
// where n : number of documents that contain the search keyword

