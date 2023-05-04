package alireza.farghadani.blueberry.list_class_View;

public class classsItem {

    private int ID;

    private String title;

    private String lesson;
    public classsItem(String title, String count_student, int ID) {
        this.title = title;
        this.lesson = count_student;
        this.ID = ID;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String count_student) {
        this.lesson = count_student;
    }


}
