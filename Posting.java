import java.util.LinkedList;

public class Posting implements Comparable<Posting> {
    private int docFreq;
    private LinkedList<String> postingList;

    public Posting(int docFreq, LinkedList<String> postingList)
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

    public LinkedList<String> getPostingList() {
        return postingList;
    }

    public void setdocId(LinkedList<String> postingList) {
        this.postingList = postingList;
    }
    
    public void incrementDocFreq() {
        docFreq++;
    }
    
    public void updatePostingList(String val) {
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
