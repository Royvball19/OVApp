package adsd.app;

public class SearchResult {


    private String title;
    private Integer posA;
    private Integer posB;

    public SearchResult(String title, Integer posA, Integer posB){
        this.title = title;
        this.posA = posA;
        this.posB = posB;
    }

    public Integer getPosA() {
        return posA;
    }

    public Integer getPosB() {
        return posB;
    }

    public String getTitle() {
        return title;
    }

    public void setPosA(Integer posA) {
        this.posA = posA;
    }

    public void setPosB(Integer posB) {
        this.posB = posB;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
