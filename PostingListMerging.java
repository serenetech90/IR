import java.util.LinkedList;

public class PostingListMerging {
	public static LinkedList<String> intersect(LinkedList<String> p1, LinkedList<String> p2) {
		LinkedList<String> answer = new LinkedList<String>();
		int step1 = 0;
		int step2 = 0;
		int n1 = p1.size();
		int n2 = p2.size();
		while(step1 < n1 && step2 < n2) {
			String docId1 = p1.get(step1);
			String docId2 = p2.get(step2);
			
			int compareValue = docId1.compareTo(docId2);
			if(compareValue == 0) { //docId1 = docId2
				answer.add(docId1);
				step1++;
				step2++;
			} else if(compareValue < 0) { //docId1 < docId2
				step1++;
			} else { //docId1 > docId2
				step2++;
			}
		}
		return answer;
	}
}
