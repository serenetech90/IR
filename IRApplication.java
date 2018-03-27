import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.instrument.Instrumentation;

public class IRApplication {
	private static String INPUT_DIR = "C:/Users/TANS0348/Desktop/testfiles/";
	private static Map<Entry<String,Integer>,LinkedList> INDEX = new LinkedHashMap<Entry<String,Integer>,LinkedList>();
	
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
            	String query = tfSearch.getText();
            	ArrayList<String> results = processQuery(query);
            	taResults.setText(String.join("\n", results));
            	long endQuery = System.currentTimeMillis();
            	showProcessingTime("query '" + query + "'", startQuery, endQuery);
            }
        });
		
		guiFrame.add(mainPanel, BorderLayout.NORTH);
		guiFrame.setVisible(true);
	}

	public static String[] tokenizeQuery(String query) {
		return null; //placeholder
	}
	
	public static String[] transformQueryTokens(String[] tokens) {
		return null; //placeholder
	}
	
	public static ArrayList<String> processQuery(String query) { 
    	String[] tokens = tokenizeQuery(query);
    	String[] modifiedTokens = transformQueryTokens(tokens);
		
    	//get the frequency and posting lists for the modified tokens
    	//Sang Sang's placeholder
    	
    	//process in ascending order of frequency
    	//Sang Sang's placeholder
    	//PostingListMerging.intersect(p1, p2);
    	
    	//placeholder
		return null;
	}
	
	public static void indexing() {
		//tokenize corpus
		//Divya's placeholder
		
		//modify tokens using linguistic modules
		//Divya's placeholder
		
		//sort modified token-docId pairs
		//Serene's placeholder
		
		//transform token-docId pairs into dictionary and postings
		//Serene's placeholder
		//this step should update INDEX
	}
	
	public static void showProcessingTime(String processDescription, long startTime, long endTime) {
		System.out.println("Time for " + processDescription + ": " + (endTime - startTime));
	}
	
	public static void showMemory(String processDescription, long totalMemory, long freeMemory) {
		System.out.println("Memory for " + processDescription + ": " + (totalMemory - freeMemory));
	}
	
	public static void main(String[] args) {
		long startIndexing = System.currentTimeMillis();
		indexing();
		long endIndexing = System.currentTimeMillis();
		showProcessingTime("indexing", startIndexing, endIndexing);
		
		Runtime runtime = Runtime.getRuntime();
		runtime.gc();
		showMemory("indexing", runtime.totalMemory(), runtime.freeMemory());
		
		new IRApplication();
	}
}