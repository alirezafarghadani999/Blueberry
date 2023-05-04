package alireza.farghadani.blueberry.list_point_View;

public class PointItem {
    private String name;
    private String family;
    private int st_ID;
    private String point_data;
    private int Topic_id;

    public PointItem(String name, String family, int st_ID,String point_data,int Topic_id) {
        this.name = name;
        this.family = family;
        this.st_ID = st_ID;
        this.point_data = point_data;
        this.Topic_id = Topic_id;
    }

    public int getTopic_id() {
        return Topic_id;
    }

    public void setTopic_id(int topic_id) {
        Topic_id = topic_id;
    }

    public String getPoint_data() {
        return point_data;
    }

    public void setPoint_data(String point_data) {
        this.point_data = point_data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public int getSt_ID() {
        return st_ID;
    }

    public void setSt_ID(int st_ID) {
        this.st_ID = st_ID;
    }


}
