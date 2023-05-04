package alireza.farghadani.blueberry.list_profile_View;

public class profileItem {
    private String title;
    private String Discription;

    public profileItem(String title, String Discription) {
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
