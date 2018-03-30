public class Pair implements Comparable<Pair> {
    private String token;
    private String docId;
    
    public Pair(String x, String y)
    {
        this.token = x;
        this.docId = y;
    }

    public Pair()
    {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String x) {
        this.token = x;
    }

    public String getdocId() {
        return docId;
    }

    public void setdocId(String y) {
        this.docId = y;
    }
    
    public int compareTo(Pair p)
    {
    	int tokenCompare = this.token.compareTo(p.token);
    	if(tokenCompare == 0) {
    		return this.docId.compareTo(p.docId);
    	} else {
    		return tokenCompare;
    	}
    }
    
    public String toString() {
    	return token + " => " + docId;
    }
}
