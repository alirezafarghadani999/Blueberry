package alireza.farghadani.blueberry.list_news_View;

public class newsItem {
    private String title;
    private String Discription;

    public newsItem(String title, String Discription) {
        this.title = title;
        this.Discription = Discription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String Discription) {
        this.Discription = Discription;
    }
}
