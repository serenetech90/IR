
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;
	
public class IRApplication {
	private static String CORPUS_DIR = "C:/Users/TANS0348/Dropbox/courses/CI6226/HillaryEmails/HillaryEmails/";
	//private static String CORPUS_DIR = "C:/Users/TANS0348/Desktop/testfiles2";
	private static Map<String, Posting> INDEX = new LinkedHashMap<String, Posting>();
	private static ArrayList<String> FILELIST;
	
	public IRApplication() {
		JFrame guiFrame = new JFrame();
		
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Information Retrieval System");
		guiFrame.setSize(560, 550);

		//center the JFrame in the middle of the screen
		guiFrame.setLocationRelativeTo(null);
		
		final JPanel mainPanel = new JPanel();
		
		final JPanel searchPanel = new JPanel();
		searchPanel.setBorder(new EmptyBorder(5, 10, 10, 10));
		searchPanel.add(new JLabel("Search: "));
		JTextField tfSearch = new JTextField(40);
		tfSearch.setMargin(new Insets(4,4,4,4));
		searchPanel.add(tfSearch);
		mainPanel.add(searchPanel);
		
		int taResultsWidth = (int) (45 * 1.5);
		JTextArea taResults = new JTextArea(26, 45);
		taResults.setEditable(false);
		taResults.setMargin(new Insets(7,7,7,7));
		JScrollPane scrollResults = new JScrollPane(taResults);
		mainPanel.add(scrollResults);
		
		tfSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String query = tfSearch.getText().trim();
            	long startQuery = System.currentTimeMillis();
            	LinkedList<Integer> results = processQuery(query);
            	long endQuery = System.currentTimeMillis();
            	
            	String showResults = "";
            	int resultSize = 0;
            	if(results == null) {
            		showResults = "Your search - " + query + " - did not match any documents.";
            	} else {
            		for(int r: results) {
            			showResults += FILELIST.get(r) + "\n";
            		}
            		
            		resultSize = results.size();
            	}
            	String processingTime = getProcessingTime(startQuery, endQuery);
            	String showSummary = resultSize + " result" + (resultSize > 1? "s": "") + " (Processing time: " + processingTime + " second" + (processingTime.equals("0")? "" : "s") + ").";
            	showSummary += "\n";
        		for(int i=0; i<taResultsWidth; i++) 
        			showSummary += "- ";
        		showSummary += "\n";
        		taResults.setText(showSummary + showResults);
            }
        });
		
		guiFrame.add(mainPanel, BorderLayout.CENTER);
		guiFrame.add(mainPanel);
		guiFrame.setVisible(true);
	}
	
	public static LinkedList<Integer> processQuery(String query) { 
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
    	
    	LinkedList<Integer> mergedList = null;
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
		ArrayList<Pair> tokenIdPairs = ProjectTokenizer.tokenizeCorpus(FILELIST);
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
	
	public static String getProcessingTime(long startTime, long endTime) {
		double timeSeconds = (endTime - startTime) / 1000.0;
		DecimalFormat df = new DecimalFormat("#.######");
		return df.format(timeSeconds);
	}
	
	public static String getMemory(long beforeMemory, long afterMemory) {
		double memoryKbytes = (afterMemory - beforeMemory) / 1024.0;
		DecimalFormat df = new DecimalFormat("#.###");
		return df.format(memoryKbytes);
	}
	
	public static void main(String[] args) {
		//get the file list
		FILELIST = ProjectToolkit.listDir(CORPUS_DIR);
		
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
