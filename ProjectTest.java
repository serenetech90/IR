package information_retrival;

import information_retrival.ProjectToolkit;
import information_retrival.ProjectTokenize;


import java.io.File;
import java.util.ArrayList;


public class ProjectTest {
	public static void main(String[] args) throws Exception {
		ArrayList<String> fileList = new ArrayList<String>();
		ArrayList<Pair> token_docId = new ArrayList<Pair>();

		String fileName = null;
		String docId = null;
		
		//fileList = ProjectToolkit.listDir("C:/Divya/NTU/Information Retrival/project/test_mail");
		fileList = ProjectToolkit.listDir("C:/Divya/NTU/Information Retrival/project/HillaryEmails");
		
		for(int i = 0; i < fileList.size(); i++){
			String content = ProjectToolkit.readTextFile(fileList.get(i));
			fileName = new File(fileList.get(i)).getName();
			docId = ProjectTokenize.getDocId(fileName);
			
			ArrayList<Pair> token_docIdTemp = ProjectTokenize.tokenize(content,docId);
			token_docId.addAll(token_docIdTemp);	
		}
		
		ArrayList<Pair> tupleNew = LinguisticModules.rmSymbolLower(token_docId);
		ArrayList<Pair> tupleStem = LinguisticModules.stemming(tupleNew);

		//for(int i=0; i < tupleStem.size(); i++){
		//	System.out.println(((Pair) tupleStem.get(i)).getdocId());
		//}
}
}
