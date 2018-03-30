import java.util.LinkedList;

public class Posting implements Comparable<Posting> {
    private int docFreq;
    private LinkedList<Integer> postingList;

    public Posting(int docFreq, LinkedList<Integer> postingList)
    {
        this.docFreq = docFreq;
        this.postingList = postingList;
    }

    public Posting()
    {
    }

    public int getDocFreq() {
        return docFreq;
    }

    public void setDocFreq(int docFreq) {
        this.docFreq = docFreq;
    }

    public LinkedList<Integer> getPostingList() {
        return postingList;
    }

    public void setdocId(LinkedList<Integer> postingList) {
        this.postingList = postingList;
    }
    
    public void incrementDocFreq() {
        docFreq++;
    }
    
    public void updatePostingList(int val) {
    	postingList.add(val);
    }
    
    public String toString() {
    	return docFreq + ": " + postingList.toString();
    }
    
    public int compareTo(Posting p)
    {
         return (this.docFreq - p.docFreq);
    }
}
