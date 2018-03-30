
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
	
public class IRApplication {
	private static String CORPUS_DIR = "C:/Users/TANS0348/Desktop/testfiles2/";
	private static Map<String, Posting> INDEX = new LinkedHashMap<String, Posting>();
	//private static Map<String, LinkedList> INDEX = new LinkedHashMap<String, LinkedList>();
	
	
	public IRApplication() {
		JFrame guiFrame = new JFrame();
		
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Information Retrieval System");
		guiFrame.setSize(580, 480);

		//center the JFrame in the middle of the screen
		guiFrame.setLocationRelativeTo(null);
		
		final JPanel mainPanel = new JPanel();
				
		mainPanel.add(new JLabel("Search: "));
		JTextField tfSearch = new JTextField(40);
		mainPanel.add(tfSearch);
		
		JTextArea taResults = new JTextArea(20, 50);
		taResults.setEditable(false);
		//mainPanel.add(new JLabel("Result(s): "));
		mainPanel.add(taResults);
		
		tfSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {            	
            	long startQuery = System.currentTimeMillis();
            	String query = tfSearch.getText().trim();
            	LinkedList<String> results = processQuery(query);
            	taResults.setText(String.join("\n", results));
            	long endQuery = System.currentTimeMillis();
            	showProcessingTime("query '" + query + "'", startQuery, endQuery);
            }
        });
		
		guiFrame.add(mainPanel, BorderLayout.NORTH);
		guiFrame.setVisible(true);
	}
	
	public static LinkedList<String> processQuery(String query) { 
    	ArrayList<String> tokens = ProjectTokenizer.tokenizeQuery(query);
    	ArrayList<String> modifiedTokens = LinguisticModules.modifyQueryTokens(tokens);
    
    	//get the posting list for the query
    	List<Posting> sortedPostings =  new ArrayList<Posting>();
    	for(String mtk: modifiedTokens) {
    		sortedPostings.add(INDEX.get(mtk));
    	}
    	
    	LinkedList<String> mergedList = null;
    	if(!sortedPostings.isEmpty()) {
    		//sort by document frequency
    		Collections.sort(sortedPostings);
    		
    		//process in ascending order of frequency
    		mergedList = sortedPostings.get(0).getPostingList();
        	for(int i = 1, n = sortedPostings.size(); i < n; i++) {
        		mergedList = PostingListMerging.intersect(mergedList, sortedPostings.get(i).getPostingList());
        	}
    	}
    	
		return mergedList;
	}
	
	/*
	public static void indexing() {
		//tokenize corpus
		ArrayList<Pair> tokenIdPairs = ProjectTokenizer.tokenizeCorpus(CORPUS_DIR);
		//debugging:
		for(Pair p: tokenIdPairs) {
			System.out.println(p.getToken() + " => " + p.getdocId());
		}
		
		//modify tokens using linguistic modules
		ArrayList<Pair> modifiedTokenIdPairs = LinguisticModules.modifyTokens(tokenIdPairs);
		//debugging:
		for(Pair p: modifiedTokenIdPairs) {
			System.out.println(p.getToken() + " => " + p.getdocId());
		}
				
		//sort modified token-docId pairs
		//transform token-docId pairs into dictionary and postings
		INDEX = Indexer.createIndex(modifiedTokenIdPairs);
		//debugging:
		for(Map.Entry<String, LinkedList> entry : INDEX.entrySet()) {
			//System.out.println(entry.getKey());
			//System.out.println(entry.getValue());
		}
	}
	*/
	
	public static void indexingTest() {
		LinkedList<String> p1 = new LinkedList<String>();
		p1.add("1");
		p1.add("2");
		p1.add("4");
		LinkedList<String> p2 = new LinkedList<String>();
		p2.add("2");
		p2.add("3");
		p2.add("4");
		p2.add("5");
		LinkedList<String> p3 = new LinkedList<String>();
		p3.add("4");
		
		Posting pt1 = new Posting(3, p1);
		Posting pt2 = new Posting(4, p2);
		Posting pt3 = new Posting(1, p3);
		
		INDEX.put("a", pt1);
		INDEX.put("b", pt2);
		INDEX.put("c", pt3);
		//debugging:
		//for(Map.Entry<String, Posting> entry : INDEX.entrySet()) {
			//System.out.println(entry.getKey());
			//System.out.println(entry.getValue());
		//}
	}
	
	
	public static void showProcessingTime(String processDescription, long startTime, long endTime) {
		System.out.println("Time for " + processDescription + ": " + (endTime - startTime));
	}
	
	public static void showMemory(String processDescription, long totalMemory, long freeMemory) {
		System.out.println("Memory for " + processDescription + ": " + (totalMemory - freeMemory));
	}
	
	public static void main(String[] args) {
		
		long startIndexing = System.currentTimeMillis();
		//indexing();
		indexingTest();
		long endIndexing = System.currentTimeMillis();
		showProcessingTime("indexing", startIndexing, endIndexing);
		
		//debugging:
		//LinkedList<String> queryTokens = processQuery("b a");
		//for(String q: queryTokens)
		//	System.out.println(q);
		
		Runtime runtime = Runtime.getRuntime();
		runtime.gc();
		showMemory("indexing", runtime.totalMemory(), runtime.freeMemory());
		
		new IRApplication();
	}
}
