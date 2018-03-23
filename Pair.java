package information_retrival;

public class Pair {
    private String token = null;
    private int docId = 0;

    public Pair(String x, int y)
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

    public int getdocId() {
        return docId;
    }
    public void setdocId(int y) {
        this.docId = y;
    }
}