package alireza.farghadani.blueberry.list_presentation_View;

public class PresentationItem {
    private int view;

    private int year;
    private int month;
    private int day;

    private int ID;
    private String student_name;
    private Boolean present;
    private String peresent_data;


    public PresentationItem(int view, int year, int month, int day, int ID, String student_name, Boolean present, String Present_data) {
        this.view = view;
        this.year = year;
        this.month = month;
        this.day = day;
        this.ID = ID;
        this.student_name = student_name;
        this.present = present;
        this.peresent_data = Present_data;

    }

    public String getPeresent_data() {
        return peresent_data;
    }

    public void setPeresent_data(String peresent_data) {
        this.peresent_data = peresent_data;
    }
    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public Boolean getPresent() {
        return present;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }
}
