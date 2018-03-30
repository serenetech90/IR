import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class Indexer {
	
	public static Map<String, Posting> createIndex(ArrayList<Pair> modifiedTokenIdPairs) {
		
		//sorting by token then by docId
		Collections.sort(modifiedTokenIdPairs);
		//debug
		//for(Pair p: modifiedTokenIdPairs) 
		//	System.out.println(p);
		
		Map<String, Posting> index = new LinkedHashMap<String, Posting>();
		
		Pair pairNeedle = new Pair();
		Posting postingPointer = null;
		for(Pair processingPair: modifiedTokenIdPairs) {
			String tk = processingPair.getToken();
			String docId = processingPair.getdocId();
			if(tk.equals(pairNeedle.getToken())) {	//same token
				if(!docId.equals(pairNeedle.getdocId())) { //different docId
					//update the doc frequency and posting list of an existing token
					postingPointer.incrementDocFreq();
					postingPointer.updatePostingList(docId);
					
					//update needle
					pairNeedle.setdocId(docId);
				}
			} else { //new/different token
				//initialize and add a new entry to index
				LinkedList<String> docIdList = new LinkedList<String>();
				docIdList.add(docId);
				Posting newPosting = new Posting(1, docIdList);
				index.put(tk, newPosting);
				
				//update needle and pointer
				pairNeedle.setToken(tk);
				pairNeedle.setdocId(docId);
				postingPointer = newPosting;
			}
		}
		
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

