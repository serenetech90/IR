
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
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;
	
public class IRApplication {
	private static String CORPUS_DIR = "C:/Users/TANS0348/Desktop/testfiles2/";
	private static Map<String, Posting> INDEX = new LinkedHashMap<String, Posting>();
	
	public IRApplication() {
		JFrame guiFrame = new JFrame();
		
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Information Retrieval System");
		guiFrame.setSize(560, 550);

		//center the JFrame in the middle of the screen
		guiFrame.setLocationRelativeTo(null);
		
		final JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(7, 10, 10, 10));
		
		final JPanel searchPanel = new JPanel();
		searchPanel.setBorder(new EmptyBorder(5, 10, 10, 10));
		searchPanel.add(new JLabel("Search: "));
		JTextField tfSearch = new JTextField(40);
		tfSearch.setMargin(new Insets(4,4,4,4));
		searchPanel.add(tfSearch);
		mainPanel.add(searchPanel);
		
		int taResultsWidth = (int) (45 * 1.57);
		JTextArea taResults = new JTextArea(28, 45);
		taResults.setEditable(false);
		taResults.setMargin(new Insets(7,7,7,7));
		mainPanel.add(taResults);
		
		tfSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String query = tfSearch.getText().trim();
            	long startQuery = System.currentTimeMillis();
            	LinkedList<String> results = processQuery(query);
            	long endQuery = System.currentTimeMillis();
            	String showText = "";
            	int resultSize = 0;
            	if(results == null) {
            		showText = "Your search - " + query + " - did not match any documents.";
            	} else {
            		showText = String.join("\n", results);
            		
            		resultSize = results.size();
            	}
            	
            	showText += "\n";
        		for(int i=0; i<taResultsWidth; i++) 
        			showText += "- ";
        		showText += "\n";
        		showText += resultSize + " result" + (resultSize > 1? "s": "") + " (Processing time: " + getProcessingTime(startQuery, endQuery) + " seconds).";
            	taResults.setText(showText);
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
    		Posting p = INDEX.get(mtk);
    		if(p == null) {	//abort if the query contains a term that doesn't exist in the corpus 
    			return null;
    		} else {
    			sortedPostings.add(INDEX.get(mtk));
    		}
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
	
	public static void indexing() {
		//tokenize corpus
		ArrayList<Pair> tokenIdPairs = ProjectTokenizer.tokenizeCorpus(CORPUS_DIR);
		//debugging:
		//for(Pair p: tokenIdPairs) {
		//	System.out.println(p);
		//}
		
		//modify tokens using linguistic modules
		ArrayList<Pair> modifiedTokenIdPairs = LinguisticModules.modifyTokens(tokenIdPairs);
		//debugging:
		//for(Pair p: modifiedTokenIdPairs) {
		//	System.out.println(p);
		//}
				
		//sort modified token-docId pairs
		//transform token-docId pairs into dictionary and postings
		INDEX = Indexer.createIndex(modifiedTokenIdPairs);
		//debugging:
		//for(Map.Entry<String, Posting> entry : INDEX.entrySet()) {
		//	System.out.println(entry.getKey() + " => " + entry.getValue());
		//}
	}
	
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
	
	
	public static String getProcessingTime(long startTime, long endTime) {
		double timeSeconds = (endTime - startTime) / 1000.0;
		DecimalFormat df = new DecimalFormat("#.####");
		return df.format(timeSeconds);
	}
	
	public static String getMemory(long beforeMemory, long afterMemory) {
		double memoryKbytes = (afterMemory - beforeMemory) / 1024.0;
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(memoryKbytes);
	}
	
	public static void main(String[] args) {
		Runtime runtime = Runtime.getRuntime();
		runtime.gc();
		runtime.gc();
		runtime.gc();
		long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
		long startIndexing = System.currentTimeMillis();
		indexing();
		//indexingTest();
		long endIndexing = System.currentTimeMillis();
		runtime.gc();
		runtime.gc();
		runtime.gc();
		long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("Time for indexing: " + getProcessingTime(startIndexing, endIndexing) + " seconds.");
		System.out.println("Memory for indexing: " + getMemory(usedMemoryBefore, usedMemoryAfter) + " kilobytes.");
		
		new IRApplication();
	}
}
