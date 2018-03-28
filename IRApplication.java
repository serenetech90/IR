
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import information_retrival.LinguisticModules;
import information_retrival.Pair;
import information_retrival.ProjectTokenize;
import information_retrival.ProjectToolkit;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.lang.instrument.Instrumentation;
import information_retrival.*;
	
public class IRApplication {
	private static String INPUT_DIR = "C:/Users/TANS0348/Desktop/testfiles/";
	private static Map<String,LinkedList> INDEX = new LinkedHashMap<String,LinkedList>();
	
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
		ArrayList<String> fileList = new ArrayList<String>();
		ArrayList<Pair> token_docId = new ArrayList<Pair>();
        
		String fileName = null;
		String docId = null;
		
		//fileList = ProjectToolkit.listDir("C:/Divya/NTU/Information Retrival/project/test_mail");
		fileList = ProjectToolkit.listDir("/home/serene/Documents/HillaryEmails/");
		
		for(int i = 0; i < fileList.size(); i++){
			String content = ProjectToolkit.readTextFile(fileList.get(i));
			fileName = new File(fileList.get(i)).getName();
			docId = ProjectTokenize.getDocId(fileName);
			
			ArrayList<Pair> token_docIdTemp = ProjectTokenize.tokenize(content,docId);
			token_docId.addAll(token_docIdTemp);	
		}
		
		//modify tokens using linguistic modules
		//Divya's placeholder
		ArrayList<Pair> tupleNew = LinguisticModules.rmSymbolLower(token_docId);
		ArrayList<Pair> tupleStem = LinguisticModules.stemming(tupleNew);

		
		//sort modified token-docId pairs
		//Serene's placeholder		
		//transform token-docId pairs into dictionary and postings
		//Serene's placeholder
		//this step should update INDEX
		INDEX = sortTokens.main(tupleStem);
		
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
