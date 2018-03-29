public class Pair {
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
}
