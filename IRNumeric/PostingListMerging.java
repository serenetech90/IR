import java.util.LinkedList;

public class PostingListMerging {
	public static LinkedList<Integer> intersect(LinkedList<Integer> p1, LinkedList<Integer> p2) {
		LinkedList<Integer> answer = new LinkedList<Integer>();
		int step1 = 0;
		int step2 = 0;
		int n1 = p1.size();
		int n2 = p2.size();
		while(step1 < n1 && step2 < n2) {
			int docId1 = p1.get(step1);
			int docId2 = p2.get(step2);
			
			if(docId1 == docId2) { //docId1 = docId2
				answer.add(docId1);
				step1++;
				step2++;
			} else if(docId1 < docId2) { //docId1 < docId2
				step1++;
			} else { //docId1 > docId2
				step2++;
			}
		}
		return answer;
	}
}
