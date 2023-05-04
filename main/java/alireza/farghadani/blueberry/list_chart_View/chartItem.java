package alireza.farghadani.blueberry.list_chart_View;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class chartItem {

    private String label ;
    private String Des ;
    private String points ;
    private int Visible_view;
    private ArrayList<Entry> point_data;

    public chartItem(String label, String des, String points, int visible_view, ArrayList<Entry> Point_data) {
        this.label = label;
        this.Des = des;
        this.points = points;
        this.Visible_view = visible_view;
        this.point_data = Point_data;
    }


    public ArrayList<Entry> getPoint_data() {
        return point_data;
    }

    public void setPoint_data(ArrayList<Entry> point_data) {
        this.point_data = point_data;
    }

    public int getVisible_view() {
        return Visible_view;
    }

    public void setVisible_view(int visible_view) {
        Visible_view = visible_view;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

}
